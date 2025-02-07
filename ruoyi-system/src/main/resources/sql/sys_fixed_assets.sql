CREATE TABLE sys_fixed_assets (
    Batch_Id VARCHAR(64) PRIMARY KEY,
    Name VARCHAR(128),
    Quantity INT,
    Unit_Price DECIMAL(10, 2),
    Depreciation_Price DECIMAL(10, 2),
    Production_Date DATE,
    Service_Life INT, -- 使用年限以年为单位
    Photo VARCHAR(256),
    Description VARCHAR(1024)
);

-- 插入示例数据
INSERT INTO sys_fixed_assets (Batch_Id, Name, Quantity, Unit_Price, Depreciation_Price, Production_Date, Service_Life, Photo, Description) 
VALUES ('ASSET001', '固定资产A', 10, 5000.00, 4500.00, '2023-01-01', 5, 'asset_photo_a.png', '这是固定资产A的描述');
INSERT INTO sys_fixed_assets (Batch_Id, Name, Quantity, Unit_Price, Depreciation_Price, Production_Date, Service_Life, Photo, Description) 
VALUES ('ASSET002', '固定资产B', 5, 10000.00, 9000.00, '2022-05-15', 10, 'asset_photo_b.png', '这是固定资产B的描述');
