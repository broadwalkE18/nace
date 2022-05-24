CREATE TABLE nacedata (
	orderid varchar(32) PRIMARY KEY,
	level int null,
	code varchar(32) null,
	parent varchar(32) null,
	description varchar(2048) null,
	itemincludes varchar(6144) null,
	alsoincludes varchar(4096) null,
	rulings varchar(2048) null,
	excludes varchar(2048) null,
	reftoisicrev4 varchar(256) null
);