CREATE PROCEDURE `sp_insertMeme`
 (
	IN in_FilePath varchar(45)
    , IN in_MemeName varchar(45)
    , IN in_MainCategory varchar(45)
    , IN in_SubCategory varchar(45)
 )
 
BEGIN
	insert into HoldingTank
    (
		FilePath
		,MemeName
		,MainCategory
		,SubCategory
		,NumberVotes
    )
    values
    (
		in_FilePath
        ,in_MemeName
        ,in_MainCategory
        ,in_SubCategory
        ,0
	);

END