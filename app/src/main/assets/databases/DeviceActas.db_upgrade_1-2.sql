CREATE TABLE "T_VERSION_DB" (`_id` INTEGER PRIMARY KEY AUTOINCREMENT,`OLD_VERSION` INTEGER,`NEW_VERSION`	INTEGER,`COMENTARIOS`	TEXT,`VERSION_PDA`	TEXT,`FECHA_REGISTRO`	INTEGER);
INSERT INTO "T_VERSION_DB" (`OLD_VERSION`,`NEW_VERSION`, `FECHA_REGISTRO`) values (1,2,DATETIME('NOW'));
