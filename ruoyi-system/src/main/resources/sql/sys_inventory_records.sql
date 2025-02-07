CREATE TABLE sys_inventory_records (
    Id VARCHAR(64) PRIMARY KEY,
    Goods_Id VARCHAR(64),
    Quantity INT,
    Record_Type VARCHAR(32), -- 记录类型，例如“入库”或“出库”
    Operator VARCHAR(64), -- 操作人
    Operation_Time TIMESTAMP -- 操作时间
);

-- 插入示例数据
INSERT INTO sys_inventory_records (Id, Goods_Id, Quantity, Record_Type, Operator, Operation_Time) 
VALUES ('RECORD001', 'BATCH001', 50, '入库', '操作员A', '2023-10-10 10:00:00');
INSERT INTO sys_inventory_records (Id, Goods_Id, Quantity, Record_Type, Operator, Operation_Time) 
VALUES ('RECORD002', 'BATCH002', 30, '出库', '操作员B', '2023-10-11 11:30:00');
