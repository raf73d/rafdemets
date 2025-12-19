insert into films(genreId,titel,voorraad,gereserveerd,prijs)
values
    ((select id from genres where naam = 'test1'),'test1',1,1,1),
    ((select id from genres where naam = 'test1'),'test2',2,2,2),
((select id from genres where naam = 'test1'),'test3',3,0,3);