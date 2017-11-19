CREATE PROCEDURE `sp_RemoveFromHoldingTank`
(
	IN in_memeID INT(11)
)
BEGIN
	DELETE FROM HoldingTank
	WHERE MemeID = in_memeID;
END