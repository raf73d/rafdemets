insert into films(genreId,titel,voorraad,gereserveerd,prijs)
values
    ((select id from genres where naam = 'test1'),'test1',10000000,1000000,1000000),
    ((select id from genres where naam = 'test1'),'test2',20000000,2000000,2000000);