
count2
===
select count(id) from #text(table_name)# where #text(sql_where)#  and recommend=1

count1
===
select count(id) from #text(table_name)# where #text(sql_where)# and hasAudit=0

hasAudit6
===
select count(id) from #text(table_name)# where  hasAudit=6

count
===
select count(id) from #text(table_name)# where #text(sql_where)#

searchAndPager
===
select * from #text(table_name)# where #text(sql_where)#

searchAndPager2
===
select * from #text(table_name)# where #text(sql_where)#  and recommend=1

searchAndPager1
===
select * from #text(table_name)# where #text(sql_where)# and hasAudit=0

hasAudit6List
===
select * from #text(table_name)# where hasAudit=6

selectSingleBySid
===
select * from #text(table_name)# where sid = #sid#

deleteByIds
===
delete from #text(table_name)# where id in (#text(ids)#)

deleteBySid
===
delete from #text(table_name)# where sid = #sid#

fetchById
===
select * from #text(table_name)# where id=#id#

fetchBySId
===
select * from #text(table_name)# where sid = #sid#