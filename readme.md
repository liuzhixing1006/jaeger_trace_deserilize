# 模块介绍
## Jaeger数据实时消费及反序列化处理Flink作业
    Jaeger的数据上报流程：Client -> Agent -> Collector -> Kafka
    
    作业定位：处于Kafka后，对Collector发送的ProtoBuf数据进行消费及反
    序列化操作，打平吐入Kafka。
    
    ********************************************* 
    其中涉及到：
        1.Flink的使用（消费、处理、打平吐入Kafka）。
        2.Jaeger Collector发送的Protobuf文件构建。
        3.Jaeger Collector发送的Bean对象反序列化。
    *********************************************