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

CREATE PROCEDURE `getMemesByTag`
 (
	IN _Tag varchar(30)
 )
BEGIN

SELECT Id
		,ImageUrl
        ,CaptionText
FROM dbo.Memes memes
LEFT JOIN dbo.MemeTags tags ON tags.MemeId = memes.Id
LEFT JOIN dbo.Likes likes ON likes.MemeId=memes.Id
WHERE tags.TagText = _Tag
ORDER BY SUM(likes.IsLike) Desc;

END