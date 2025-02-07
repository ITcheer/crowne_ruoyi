/*
 Navicat Premium Data Transfer

 Source Server         : aliyunclown
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : clown-dbouter.mysql.rds.aliyuncs.com:7777
 Source Schema         : rouyi

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 05/02/2025 15:11:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由名称',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2119 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, '', '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2024-12-11 16:18:00', '', NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, '', '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2024-12-11 16:18:00', '', NULL, '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, '', '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2024-12-11 16:18:00', '', NULL, '系统工具目录');
INSERT INTO `sys_menu` VALUES (4, '若依官网', 0, 4, 'http://ruoyi.vip', NULL, '', '', 0, 0, 'M', '1', '0', '', 'guide', 'admin', '2024-12-11 16:18:00', 'admin', '2024-12-12 11:50:58', '若依官网地址');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2024-12-11 16:18:00', '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2024-12-11 16:18:00', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2024-12-11 16:18:00', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2024-12-11 16:18:00', '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2024-12-11 16:18:00', '', NULL, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2024-12-11 16:18:00', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2024-12-11 16:18:00', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2024-12-11 16:18:00', '', NULL, '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2024-12-11 16:18:00', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2024-12-11 16:18:00', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2024-12-11 16:18:00', '', NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2024-12-11 16:18:00', '', NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2024-12-11 16:18:00', '', NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2024-12-11 16:18:00', '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', 'admin', '2024-12-11 16:18:00', '', NULL, '缓存列表菜单');
INSERT INTO `sys_menu` VALUES (115, '表单构建', 3, 1, 'build', 'tool/build/index', '', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2024-12-11 16:18:00', '', NULL, '表单构建菜单');
INSERT INTO `sys_menu` VALUES (116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2024-12-11 16:18:00', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2024-12-11 16:18:00', '', NULL, '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2024-12-11 16:18:00', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2024-12-11 16:18:00', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '部门查询', 103, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门新增', 103, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门修改', 103, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门删除', 103, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '公告查询', 107, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告新增', 107, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告修改', 107, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告删除', 107, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '日志导出', 500, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '登录查询', 501, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录删除', 501, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '日志导出', 501, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '账户解锁', 501, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 116, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 116, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 116, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 116, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 116, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 116, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2024-12-11 16:18:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, 'GUIS报单前台', 0, 5, 'https://schoolmaintain-webapp.azurewebsites.net/', NULL, NULL, '', 0, 0, 'M', '0', '0', '', 'tab', 'admin', '2024-12-12 13:26:02', 'admin', '2024-12-25 10:24:30', '');
INSERT INTO `sys_menu` VALUES (2001, '测试', 1, 10, 'test', 'system/test/index', NULL, '', 1, 0, 'C', '0', '0', 'system:test:list', 'code', 'admin', '2024-12-18 10:13:54', 'crown', '2024-12-18 13:33:27', '');
INSERT INTO `sys_menu` VALUES (2002, '全部工单', 2003, 1, 'all', 'maintenanceOrder/all/index', NULL, '', 1, 0, 'C', '0', '0', 'maintenanceOrder:all:list', 'form', 'admin', '2024-12-18 18:59:23', 'admin', '2025-01-24 17:29:44', '');
INSERT INTO `sys_menu` VALUES (2003, '维修工单', 0, 7, 'maintenanceOrder', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'documentation', 'admin', '2024-12-23 09:25:00', 'admin', '2024-12-23 15:05:02', '');
INSERT INTO `sys_menu` VALUES (2005, '我的工单', 2003, 2, 'my', 'maintenanceOrder/my/index', NULL, '', 1, 0, 'C', '0', '0', 'maintenanceOrder:my:list', 'form', 'admin', '2024-12-23 14:46:18', 'admin', '2025-01-24 01:41:38', '');
INSERT INTO `sys_menu` VALUES (2006, '用户查询', 2005, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:my:query', '#', 'admin', '2024-12-23 17:02:16', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2007, '数据导出', 2002, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:all:export', '#', 'admin', '2024-12-23 17:03:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2008, '工单查询', 2002, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:all:query', '#', 'admin', '2024-12-23 17:04:09', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2009, '新增工单', 2002, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:all:add', '#', 'admin', '2024-12-23 17:04:26', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2010, '修改工单', 2002, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:all:edit', '#', 'admin', '2024-12-23 17:05:11', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2011, '删除工单', 2002, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:all:remove', '#', 'admin', '2024-12-23 17:05:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2012, '分配工单', 2002, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:all:assign', '#', 'admin', '2024-12-23 17:05:48', 'admin', '2024-12-23 17:08:07', '');
INSERT INTO `sys_menu` VALUES (2013, '图片上传', 2002, 7, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:all:upload', '#', 'admin', '2024-12-23 17:06:22', 'admin', '2024-12-23 17:08:16', '');
INSERT INTO `sys_menu` VALUES (2014, '提交更新', 2002, 8, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:all:submitOrUpdate', '#', 'admin', '2024-12-23 17:37:44', 'admin', '2025-01-24 01:52:39', '');
INSERT INTO `sys_menu` VALUES (2015, '图片上传', 2005, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:my:upload', '#', 'admin', '2024-12-25 10:55:03', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2016, '修改工单', 2005, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:my:edit', '#', 'admin', '2024-12-25 11:06:48', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2017, '数据导出', 2005, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:my:export', '#', 'admin', '2024-12-25 11:28:08', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2018, '秩序巡逻', 0, 9, 'patrol', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2024-12-30 11:11:30', 'admin', '2025-01-24 17:54:17', '');
INSERT INTO `sys_menu` VALUES (2019, '巡逻地点', 2018, 1, 'location', 'patrol/location/index', NULL, '', 1, 0, 'C', '0', '0', 'patrol:location:list', 'example', 'admin', '2024-12-30 11:12:55', 'admin', '2024-12-30 11:13:35', '');
INSERT INTO `sys_menu` VALUES (2020, '巡逻任务', 2018, 2, 'task', 'patrol/task/index', NULL, '', 1, 0, 'C', '0', '0', 'patrol:task:list', 'clipboard', 'admin', '2025-01-07 11:37:07', 'admin', '2025-01-07 11:37:18', '');
INSERT INTO `sys_menu` VALUES (2021, '个人消息', 0, 11, 'notification', NULL, NULL, '', 1, 0, 'M', '1', '0', '', 'message', 'admin', '2025-01-14 10:38:41', 'admin', '2025-01-26 14:05:44', '');
INSERT INTO `sys_menu` VALUES (2022, '消息列表', 2021, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'notification:personal:list', '#', 'admin', '2025-01-14 10:40:20', 'admin', '2025-01-14 10:43:47', '');
INSERT INTO `sys_menu` VALUES (2023, '消息查询', 2021, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'notification:personal:query', '#', 'admin', '2025-01-14 10:43:41', 'admin', '2025-01-14 10:43:52', '');
INSERT INTO `sys_menu` VALUES (2024, '新增消息', 2021, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'notification:personal:add', '#', 'admin', '2025-01-14 10:44:11', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2025, '修改消息', 2021, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'notification:personal:edit', '#', 'admin', '2025-01-14 10:44:33', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2026, '删除消息', 2021, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'notification:personal:remove', '#', 'admin', '2025-01-14 10:44:59', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2027, '数据导出', 2019, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:location:export', '#', 'admin', '2025-01-14 10:57:21', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2028, '详细查询', 2019, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:location:query', '#', 'admin', '2025-01-14 10:57:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2029, '地点新增', 2019, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:location:add', '#', 'admin', '2025-01-14 10:58:13', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2030, '地点修改', 2019, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:location:edit', '#', 'admin', '2025-01-14 10:58:37', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2031, '地点删除', 2019, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:location:remove', '#', 'admin', '2025-01-14 10:59:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2032, '图片上传', 2019, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:location:upload', '#', 'admin', '2025-01-14 10:59:28', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2033, '数据导出', 2020, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:task:export', '#', 'admin', '2025-01-14 11:01:59', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2034, '详细查询', 2020, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:task:query', '#', 'admin', '2025-01-14 11:02:19', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2035, '任务新增', 2020, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:task:add', '#', 'admin', '2025-01-14 11:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2036, '任务修改', 2020, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:task:edit', '#', 'admin', '2025-01-14 11:03:06', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2037, '任务删除', 2020, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:task:remove', '#', 'admin', '2025-01-14 11:03:25', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2038, '图片上传', 2020, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'patrol:task:upload', '#', 'admin', '2025-01-14 11:03:50', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2039, '清洁工单', 0, 8, 'cleaningOrder', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'documentation', 'admin', '2025-01-23 12:45:40', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2040, '全部工单', 2039, 1, 'all', 'cleaningOrder/all/index', NULL, '', 1, 0, 'C', '0', '0', 'cleaningOrder:all:list', 'form', 'admin', '2025-01-23 12:46:54', 'admin', '2025-01-23 12:47:38', '');
INSERT INTO `sys_menu` VALUES (2041, '我的工单', 2039, 2, 'my', 'cleaningOrder/my/index', NULL, '', 1, 0, 'C', '0', '0', 'cleaningOrder:my:list', 'form', 'admin', '2025-01-24 01:42:19', 'admin', '2025-01-24 01:43:12', '');
INSERT INTO `sys_menu` VALUES (2042, '数据导出', 2040, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:all:export', '#', 'admin', '2025-01-24 01:49:01', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2043, '工单查询', 2040, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:all:query', '#', 'admin', '2025-01-24 01:49:48', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2044, '新增工单', 2040, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:all:add', '#', 'admin', '2025-01-24 01:50:21', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2045, '修改工单', 2040, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:all:edit', '#', 'admin', '2025-01-24 01:50:50', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2046, '删除工单', 2040, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:all:remove', '#', 'admin', '2025-01-24 01:51:27', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2047, '分配工单', 2040, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:all:assign', '#', 'admin', '2025-01-24 01:51:56', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2048, '图片上传', 2040, 7, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:all:upload', '#', 'admin', '2025-01-24 01:52:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2049, '提交更新', 2040, 8, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:all:submitOrUpdate', '#', 'admin', '2025-01-24 01:53:41', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2050, '用户查询', 2041, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:my:query', '#', 'admin', '2025-01-24 01:54:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2051, '图片上传', 2041, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:my:upload', '#', 'admin', '2025-01-24 01:54:49', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2052, '修改工单', 2041, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:my:edit', '#', 'admin', '2025-01-24 01:55:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2053, '数据导出', 2041, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:my:export', '#', 'admin', '2025-01-24 01:55:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2054, '完成邮件', 2005, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:my:sendCompletionEmail', '#', 'admin', '2025-01-24 13:41:21', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2055, '失败邮件', 2005, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:my:sendIncompleteEmail', '#', 'admin', '2025-01-24 13:41:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2056, '失败邮件', 2041, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:my:sendIncompleteEmail', '#', 'admin', '2025-01-24 13:42:11', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2057, '完成邮件', 2041, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'cleaningOrder:my:sendCompletionEmail', '#', 'admin', '2025-01-24 13:42:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2058, '校工工单', 0, 8, 'facilityOrder', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'documentation', 'admin', '2025-01-24 14:56:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2059, '全部工单', 2058, 1, 'all', 'facilityOrder/all/index', NULL, '', 1, 0, 'C', '0', '0', 'facilityOrder:all:list', 'form', 'admin', '2025-01-24 14:58:03', 'admin', '2025-01-24 16:42:08', '');
INSERT INTO `sys_menu` VALUES (2060, '我的工单', 2058, 2, 'my', 'facilityOrder/my/index', NULL, '', 1, 0, 'C', '0', '0', 'facilityOrder:my:list', 'form', 'admin', '2025-01-24 16:42:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2061, '数据导出', 2059, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:all:export', '#', 'admin', '2025-01-24 17:30:53', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2062, '工单查询', 2059, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:all:query', '#', 'admin', '2025-01-24 17:31:10', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2063, '新增工单', 2059, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:all:add', '#', 'admin', '2025-01-24 17:31:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2064, '修改工单', 2059, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:all:edit', '#', 'admin', '2025-01-24 17:31:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2065, '删除工单', 2059, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:all:remove', '#', 'admin', '2025-01-24 17:32:04', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2066, '分配工单', 2059, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:all:assign', '#', 'admin', '2025-01-24 17:32:32', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2067, '图片上传', 2059, 7, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:all:upload', '#', 'admin', '2025-01-24 17:33:01', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2068, '提交更新', 2059, 8, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:all:submitOrUpdate', '#', 'admin', '2025-01-24 17:33:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2069, '用户查询', 2060, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:my:query', '#', 'admin', '2025-01-24 17:34:19', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2070, '图片上传', 2060, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:my:upload', '#', 'admin', '2025-01-24 17:34:41', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2071, '修改工单', 2060, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:my:edit', '#', 'admin', '2025-01-24 17:34:56', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2072, '数据导出', 2060, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:my:export', '#', 'admin', '2025-01-24 17:35:11', 'admin', '2025-01-24 17:35:21', '');
INSERT INTO `sys_menu` VALUES (2073, '完成邮件', 2060, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:my:sendCompletionEmail', '#', 'admin', '2025-01-24 17:36:01', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2074, '失败邮件', 2060, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'facilityOrder:my:sendIncompleteEmail', '#', 'admin', '2025-01-24 17:36:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2075, '当前用户信息', 0, 11, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'maintenanceOrder:my:query', '#', 'admin', '2025-01-24 17:52:35', 'admin', '2025-01-26 14:05:51', '');
INSERT INTO `sys_menu` VALUES (2076, '投诉工单', 0, 8, 'complaintOrder', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'documentation', 'admin', '2025-01-25 13:26:57', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2077, '全部工单', 2076, 1, 'all', 'complaintOrder/all/index', NULL, '', 1, 0, 'C', '0', '0', 'complaintOrder:all:list', 'form', 'admin', '2025-01-25 13:27:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2078, '工程巡视', 0, 9, 'engineering', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'log', 'admin', '2025-01-25 18:05:26', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2079, '工程地点', 2078, 1, 'patrolLocation', 'engineering/location/index', NULL, '', 1, 0, 'C', '0', '0', 'engineering:patrolLocation:list', 'example', 'admin', '2025-01-25 18:06:22', 'admin', '2025-01-25 18:12:10', '');
INSERT INTO `sys_menu` VALUES (2080, '巡视任务', 2078, 2, 'patrolTask', 'engineering/task/index', NULL, '', 1, 0, 'C', '0', '0', 'engineering:patrolTask:list', 'clipboard', 'admin', '2025-01-25 18:07:22', 'admin', '2025-01-25 18:12:20', '');
INSERT INTO `sys_menu` VALUES (2081, '库房管理', 0, 10, 'storeroom', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'shopping', 'admin', '2025-01-26 14:05:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2082, '普通货品', 2081, 1, 'goods', 'storeroom/goods/index', NULL, '', 1, 0, 'C', '0', '0', 'storeroom:goods:list', 'list', 'admin', '2025-01-26 14:06:51', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2083, '固定资产', 2081, 2, 'fixedAssets', 'storeroom/fixedAssets/index', NULL, '', 1, 0, 'C', '0', '0', 'storeroom:fixedAssets:list', 'list', 'admin', '2025-01-26 15:24:18', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2084, '数据导出', 2079, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolLocation:export', '#', 'admin', '2025-01-26 17:17:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2085, '详细查询', 2079, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolLocation:query', '#', 'admin', '2025-01-26 17:18:03', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2086, '地点新增', 2079, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolLocation:add', '#', 'admin', '2025-01-26 17:18:17', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2087, '地点修改', 2079, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolLocation:edit', '#', 'admin', '2025-01-26 17:18:35', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2088, '地点删除', 2079, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolLocation:remove', '#', 'admin', '2025-01-26 17:19:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2089, '图片上传', 2079, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolLocation:upload', '#', 'admin', '2025-01-26 17:19:18', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2090, '数据导出', 2080, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolTask:export', '#', 'admin', '2025-01-26 17:19:51', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2091, '详细查询', 2080, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolTask:query', '#', 'admin', '2025-01-26 17:20:11', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2092, '任务新增', 2080, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolTask:add', '#', 'admin', '2025-01-26 17:20:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2093, '任务修改', 2080, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolTask:edit', '#', 'admin', '2025-01-26 17:20:36', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2094, '任务删除', 2080, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolTask:remove', '#', 'admin', '2025-01-26 17:20:48', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2095, '图片上传', 2080, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'engineering:patrolTask:upload', '#', 'admin', '2025-01-26 17:21:01', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2096, '数据导出', 2082, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:goods:export', '#', 'admin', '2025-01-26 17:21:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2097, '货品新增', 2082, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:goods:add', '#', 'admin', '2025-01-26 17:22:06', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2098, '数据查询', 2082, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:goods:query', '#', 'admin', '2025-01-26 17:22:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2099, '数据修改', 2082, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:goods:edit', '#', 'admin', '2025-01-26 17:22:48', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2100, '货品删除', 2082, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:goods:remove', '#', 'admin', '2025-01-26 17:23:09', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2101, '图片上传', 2082, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:goods:upload', '#', 'admin', '2025-01-26 17:23:37', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2102, '出入库', 2082, 7, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:goods:inventory', '#', 'admin', '2025-01-26 17:24:14', 'admin', '2025-01-26 17:24:21', '');
INSERT INTO `sys_menu` VALUES (2103, '数据导出', 2083, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:fixedAssets:export', '#', 'admin', '2025-01-26 17:24:47', 'admin', '2025-01-26 17:25:31', '');
INSERT INTO `sys_menu` VALUES (2104, '资产新增', 2083, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:fixedAssets:add', '#', 'admin', '2025-01-26 17:25:17', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2105, '数据查询', 2083, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:fixedAssets:query', '#', 'admin', '2025-01-26 17:25:49', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2106, '数据修改', 2083, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:fixedAssets:edit', '#', 'admin', '2025-01-26 17:26:04', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2107, '资产删除', 2083, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:fixedAssets:remove', '#', 'admin', '2025-01-26 17:26:22', 'admin', '2025-01-26 17:26:57', '');
INSERT INTO `sys_menu` VALUES (2108, '图片上传', 2083, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:fixedAssets:upload', '#', 'admin', '2025-01-26 17:26:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2109, '出入库', 2083, 7, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'storeroom:fixedAssets:inventory', '#', 'admin', '2025-01-26 17:27:18', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2110, '首页接口', 0, 11, 'homePage', NULL, NULL, '', 1, 0, 'M', '1', '0', NULL, 'education', 'admin', '2025-01-26 21:13:04', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2111, '我的工单数据', 2110, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'homePage:myOrder:list', '#', 'admin', '2025-01-26 21:13:33', 'admin', '2025-01-26 21:14:33', '');
INSERT INTO `sys_menu` VALUES (2112, '全部工单数据', 2110, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'homePage:allOrders:list', '#', 'admin', '2025-01-26 21:14:49', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2113, '数据导出', 2077, 1, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'complaintOrder:all:export', '#', 'admin', '2025-02-03 14:39:03', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2114, '工单查询', 2077, 2, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'complaintOrder:all:query', '#', 'admin', '2025-02-03 14:39:33', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2115, '新增工单', 2077, 3, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'complaintOrder:all:add', '#', 'admin', '2025-02-03 14:39:51', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2116, '修改工单', 2077, 4, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'complaintOrder:all:edit', '#', 'admin', '2025-02-03 14:40:19', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2117, '删除工单', 2077, 5, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'complaintOrder:all:remove', '#', 'admin', '2025-02-03 14:40:53', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2118, '处理工单', 2077, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'complaintOrder:all:process', '#', 'admin', '2025-02-03 14:43:34', '', NULL, '');

SET FOREIGN_KEY_CHECKS = 1;
