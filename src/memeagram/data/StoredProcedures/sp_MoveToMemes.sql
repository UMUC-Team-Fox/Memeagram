CREATE PROCEDURE `sp_MoveToMemes`
 (
	IN in_memeID INT(11)
 )
 
BEGIN

	DECLARE
		UID INT(11)
		,imgURL varchar(250)
		, cptnText varchar(500)
		, mainCat varchar(60)
		, subCat varchar(60);
		
		SELECT 
			UserID
			,FilePath
			,CaptionText
			,MainCategory
			,SubCategory
		INTO 
			UID
			,imgURL
			,cptnText
			, mainCat
			, subCat
		FROM HoldingTank WHERE MemeID = in_memeID;
		
		INSERT INTO Memes
		(
			Id
			,ImageUrl
			,CaptionText
		)
		VALUES
		(
			in_memeID
			,imgURL
			,cptnText
		)
		
		SET @Tags := CONCAT(mainCat,',',subCat);
	
		INSERT INTO MemeTags
		(
			MemeID
			,TagText
		)
		VALUES
		(
			in_memeID
			,@Tags
		);
	
		exec sp_RemoveFromHoldingTank(in_memeID);

END