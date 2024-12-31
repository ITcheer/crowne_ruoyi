CREATE TABLE `sys_test` (
  `test_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `test_name` varchar(50) DEFAULT NULL,
  `test_key` varchar(50) DEFAULT NULL,
  `test_value` varchar(500) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` varchar(1) DEFAULT '0',
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
