CREATE TABLE sys_goods (
    Batch_Id VARCHAR(64) PRIMARY KEY,
    Name VARCHAR(128),
    Quantity INT,
    Unit_Price DECIMAL(10, 2),
    Production_Date DATE,
    Shelf_Life INT, -- 保质期以月为单位
    Photo VARCHAR(256),
    Description VARCHAR(1024)
);

-- 插入示例数据
INSERT INTO sys_goods (Batch_Id, Name, Quantity, Unit_Price, Production_Date, Shelf_Life, Photo, Description) 
VALUES ('BATCH001', '普通货品A', 100, 10.50, '2023-10-01', 12, 'photo_a.png', '这是普通货品A的描述');
INSERT INTO sys_goods (Batch_Id, Name, Quantity, Unit_Price, Production_Date, Shelf_Life, Photo, Description) 
VALUES ('BATCH002', '普通货品B', 200, 20.00, '2023-09-15', 6, 'photo_b.png', '这是普通货品B的描述');
