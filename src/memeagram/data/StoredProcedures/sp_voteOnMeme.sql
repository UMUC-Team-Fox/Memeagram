CREATE PROCEDURE `sp_voteOnMeme`
 (
	IN in_memeID INT(11)
 )
 
BEGIN

	DECLARE votes INT;
	SELECT NumberOfVotes INTO votes FROM HoldingTank WHERE MemeID=in_memeID;
	
	UPDATE HoldingTank
	SET
		NumberOfVotes = votes + 1
	WHERE MemeID = in_memeID;	
	
	if (votes >= 49)
	BEGIN
		exec sp_MoveToMemes(in_memeID);
	END
END