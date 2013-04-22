package com.github.mybridge.sharding;

/**
 * http://blog.csdn.net/bluishglc/article/details/7970268
 * <pre>
 *    第一阶段:
 *      
 *    |<---------------------------------  0 - 4000w  -------------------------------------------->|
 * 	  |                                                                                            |
 *    +--------------------------------------------------------------------------------------------+
 *    |           id%2=0                        |					id%2=1                         |
 *    +--------------------------------------------------------------------------------------------+
 *    |   +--------------+-----------------+    |      +-----------------+-------------------+     |
 *    |   |   0-2000W    |  2000W-4000W    |    |      |    0-2000W      |    2000W-4000W    |     |
 *    |   +--------------+-----------------+    |      +-----------------+-------------------+     |
 *    |   |              |                 |    |      |                 |                   |     |
 *    |   |  table_0     |  table_1        |    |      |     table_0     |    table_1        |     |
 *    |   |              |                 |    |      |                 |                   |     |    
 *    |   +--------------+-----------------+    |      +-----------------+-------------------+     |
 *    +--------------------------------------------------------------------------------------------+
 *    |               Shard0                    |                      Shard1                      |
 *    +--------------------------------------------------------------------------------------------+
 *    |                                   ShardGroup_0                                             |
 *    +--------------------------------------------------------------------------------------------+
 *    
 *    第二阶段：在第一阶段基础上扩容
 *    三台服务性能：Shard2 < Shard3 < Shard4
 *    |<---------------------------------  4000w - 10000w  ------------------------------------------->|
 * 	  |                                                                                                |
 *    +------------------------------------------------------------------------------------------------+
 *    |      id%6=0    |            id%6=1,2            |					id%6=3,4,5                 |
 *    +------------------------------------------------------------------------------------------------+
 *    | +------------+ | +------------+ +-------------+ | +------------+ +------------+ +------------+ |
 *    | |4000w-10000W| | |4000W-7000W | |7000w-10000W | | |4000W-6000w | |6000W-8000w | |8000W-10000w| |  
 *    | +------------+ | +------------+ +-------------+ | +------------+ +------------+ +------------+ |
 *    | |            | | |            | |             | | |            | |            | |            | |
 *    | |  table_0   | | |  table_0   | | table_1     | | | table_0    | | table_1    | | table_2    | |
 *    | |            | | |            | |             | | |            | |            | |            | |
 *    | +------------+ | +------------+ +-------------+ | +------------+ +------------+ +------------+ |
 *    +------------------------------------------------------------------------------------------------+
 *    |      Shard2    |           Shard3               |                 Shard4                       |
 *    +------------------------------------------------------------------------------------------------+
 *    |                                   ShardGroup_1                                                 |
 *    +------------------------------------------------------------------------------------------------+
 * </pre>
 */
