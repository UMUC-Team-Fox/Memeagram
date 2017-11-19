CREATE PROCEDURE `sp_insertMeme`
(
  IN _userId int(11)
  , IN _imageUrl varchar(250)
  , IN _captionText varchar(500)
)
 
BEGIN
	insert into Memes
  (
    UserId
    ,ImageUrl
    ,CaptionText
  )
  values
  (
    _userId
    ,_imageUrl
    ,_captionText
  );
END