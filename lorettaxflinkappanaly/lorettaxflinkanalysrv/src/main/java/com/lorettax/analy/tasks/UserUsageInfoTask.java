package com.lorettax.analy.tasks;

import com.lorettax.analy.Mockcommon.Startup;
import com.lorettax.analy.Mockcommon.UsageInfo;
import com.lorettax.analy.WatermarkAndTimestamp.CustomWatermarkExtractor;
import com.lorettax.analy.maps.StartupMap;
import com.lorettax.analy.maps.UsageInfoMap;
import com.lorettax.analy.reduces.StartupReduce;
import com.lorettax.analy.reduces.UsageInfoReduce;
import com.lorettax.analy.sinks.StartupSink;
import com.lorettax.analy.sinks.UsageInfoSink;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.scala.DataStream;
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

public class UserUsageInfoTask {

    public static void main(String[] args) throws Exception{
        args = new String[]{"--input-topic","usageinfo","--bootstrap.servers","127.0.0.1:9092","--zookeeper.connect","127.0.0.1:2181","--group.id","myconsumer"};
        final ParameterTool parameterTool = ParameterTool.fromArgs(args);
        //		if (parameterTool.getNumberOfParameters() < 5) {
//			System.out.println("Missing parameters!\n" +
//					"Usage: Kafka --input-topic <topic> --output-topic <topic> " +
//					"--bootstrap.servers <kafka brokers> " +
//					"--zookeeper.connect <zk quorum> --group.id <some id>");
//			return;
//		}

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4,10000));
        env.enableCheckpointing(5000);// create a checkpoint every 5 seconds
        env.getConfig().setGlobalJobParameters(parameterTool);// make parameters available in the web interface
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


        DataStream<KafkaEvent> input = env.addSource(new FlinkKafkaConsumer010<KafkaEvent>(parameterTool.getRequired("input_topic"),
                (DeserializationSchema<KafkaEvent>) new KafkaEventSchema(),
                parameterTool.getProperties())
                .assignTimestampsAndWatermarks(new CustomWatermarkExtractor()));

        DataStream<UsageInfo> usageInfomap = input.map(new UsageInfoMap());

        DataStream<UsageInfo> usageInforeduce = usageInfomap.keyBy("groupbyfield")
                .timeWindowAll(Time.seconds(2)).reduce(new UsageInfoReduce());

        usageInforeduce.addSink(new UsageInfoSink());

        env.execute("usage analy");
    }

}
