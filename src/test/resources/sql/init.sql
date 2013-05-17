INSERT INTO `shard_group` VALUES ('0', 'user_0', '1', '0', '10000', now(), now());

INSERT INTO `shard` VALUES ('0', 'shard_0', '0', '0', '1', now(), now());
INSERT INTO `shard` VALUES ('1', 'shard_1', '1', '0', '1', now(), now());

INSERT INTO `fragment_table` VALUES ('0', 'user_0', '0', '0', '5000', '2013-05-17 14:24:55', '2013-05-17 14:24:58');
INSERT INTO `fragment_table` VALUES ('1', 'user_1', '1', '5000', '10000', '2013-05-17 14:25:39', '2013-05-17 14:25:42');

INSERT INTO `node` VALUES ('0', '10.28.168.53', '3306', '0', '0', '1','1', '2013-05-17 15:37:27', '2013-05-17 15:37:30');