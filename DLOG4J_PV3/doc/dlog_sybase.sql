/*==============================================================*/
/* DBMS name:      Sybase AS Enterprise 11.0                    */
/* Created on:     2006-9-5 10:17:01                            */
/*==============================================================*/


/*==============================================================*/
/* Table: DLOG_ALBUM                                            */
/*==============================================================*/
CREATE TABLE DLOG_ALBUM (
     ALBUM_ID             NUMERIC                        IDENTITY,
     PHOTO_ID             NUMERIC                        NULL,
     DLO_ALBUM_ID         NUMERIC                        NULL,
     SITE_ID              NUMERIC                        NOT NULL,
     DLOG_TYPE_ID         NUMERIC                        NULL,
     ALBUM_NAME           VARCHAR(40)                    NOT NULL,
     ALBUM_DESC           VARCHAR(200)                   NULL,
     PHOTO_COUNT          INT                            NOT NULL,
     ALBUM_TYPE           INT                            NOT NULL,
     VERIFYCODE           VARCHAR(20)                    NULL,
     SORT_ORDER           INT                            NOT NULL,
     CREATE_TIME          DATETIME                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_BLOCKED_IP                                       */
/*==============================================================*/
CREATE TABLE DLOG_BLOCKED_IP (
     BLOCKED_IP_ID        NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NULL,
     IP_ADDR              INT                            NOT NULL,
     S_IP_ADDR            VARCHAR(16)                    NOT NULL,
     IP_MASK              INT                            NOT NULL,
     S_IP_MASK            VARCHAR(16)                    NOT NULL,
     BLOCKED_TYPE         SMALLINT                       NOT NULL,
     BLOCKED_TIME         DATETIME                       NOT NULL,
     STATUS               SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_BOOKMARK                                         */
/*==============================================================*/
CREATE TABLE DLOG_BOOKMARK (
     MARK_ID              NUMERIC                        IDENTITY,
     USERID               NUMERIC                        NOT NULL,
     SITE_ID              NUMERIC                        NOT NULL,
     PARENT_ID            INT                            NOT NULL,
     PARENT_TYPE          SMALLINT                       NOT NULL,
     TITLE                VARCHAR(200)                   NULL,
     URL                  VARCHAR(200)                   NULL,
     CREATE_TIME          DATETIME                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_BULLETIN                                         */
/*==============================================================*/
CREATE TABLE DLOG_BULLETIN (
     BULLETIN_ID          NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NOT NULL,
     BULLETIN_TYPE        INT                            NOT NULL,
     PUB_TIME             DATETIME                       NOT NULL,
     STATUS               SMALLINT                       NOT NULL,
     TITLE                VARCHAR(200)                   NOT NULL,
     CONTENT              TEXT                           NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_CATALOG                                          */
/*==============================================================*/
CREATE TABLE DLOG_CATALOG (
     CATALOG_ID           NUMERIC                        IDENTITY,
     DLOG_TYPE_ID         NUMERIC                        NULL,
     SITE_ID              NUMERIC                        NOT NULL,
     CATALOG_NAME         VARCHAR(20)                    NOT NULL,
     CATALOG_DESC         VARCHAR(200)                   NULL,
     CREATE_TIME          DATETIME                       NOT NULL,
     ARTICLE_COUNT        INT                            NOT NULL,
     CATALOG_TYPE         SMALLINT                       NOT NULL,
     VERIFYCODE           VARCHAR(20)                    NULL,
     SORT_ORDER           INT                            NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_CATALOG_PERM                                     */
/*==============================================================*/
CREATE TABLE DLOG_CATALOG_PERM (
     CATALOG_ID           NUMERIC                        NOT NULL,
     USERID               NUMERIC                        NOT NULL,
     USER_ROLE            INT                            NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_COMMENTS                                         */
/*==============================================================*/
CREATE TABLE DLOG_COMMENTS (
     COMMENT_ID           NUMERIC                        IDENTITY,
     DLO_COMMENT_ID       NUMERIC                        NULL,
     SITE_ID              NUMERIC                        NULL,
     ENTITY_ID            INT                            NOT NULL,
     ENTITY_TYPE          INT                            NOT NULL,
     CLIENT_IP            VARCHAR(16)                    NOT NULL,
     CLIENT_TYPE          SMALLINT                       NOT NULL,
     CLIENT_USER_AGENT    VARCHAR(100)                   NULL,
     AUTHOR_ID            INT                            NOT NULL,
     AUTHOR               VARCHAR(20)                    NOT NULL,
     AUTHOR_EMAIL         VARCHAR(50)                    NULL,
     AUTHOR_URL           VARCHAR(100)                   NULL,
     TITLE                VARCHAR(200)                   NOT NULL,
     CONTENT              TEXT                           NOT NULL,
     CONTENT_FORMAT       SMALLINT                       NOT NULL,
     COMMENT_TIME         DATETIME                       NOT NULL,
     COMMENT_FLAG         SMALLINT                       NOT NULL,
     COMMENT_STATUS       SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_CONFIG                                           */
/*==============================================================*/
CREATE TABLE DLOG_CONFIG (
     CONFIG_ID            NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NULL,
     CONFIG_NAME          VARCHAR(20)                    NOT NULL,
     INT_VALUE            INT                            NULL,
     STRING_VALUE         VARCHAR(100)                   NULL,
     DATE_VALUE           DATETIME                       NULL,
     TIME_VALUE           DATETIME                       NULL,
     TIMESTAMP_VALUE      DATETIME                       NULL,
     LAST_UPDATE          TIMESTAMP                      NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_DIARY                                            */
/*==============================================================*/
CREATE TABLE DLOG_DIARY (
     DIARY_ID             NUMERIC                        IDENTITY,
     USERID               NUMERIC                        NOT NULL,
     SITE_ID              NUMERIC                        NOT NULL,
     CATALOG_ID           NUMERIC                        NOT NULL,
     AUTHOR               VARCHAR(20)                    NOT NULL,
     AUTHOR_URL           VARCHAR(100)                   NULL,
     TITLE                VARCHAR(200)                   NOT NULL,
     CONTENT              TEXT                           NOT NULL,
     DIARY_SIZE           INT                            NOT NULL,
     REFER                VARCHAR(100)                   NULL,
     WEATHER              VARCHAR(20)                    NOT NULL,
     MOOD_LEVEL           SMALLINT                       NOT NULL,
     TAGS                 VARCHAR(100)                   NULL,
     BGSOUND              INT                            NULL,
     REPLY_COUNT          INT                            NOT NULL,
     VIEW_COUNT           INT                            NOT NULL,
     TB_COUNT             INT                            NOT NULL,
     CLIENT_TYPE          SMALLINT                       NOT NULL,
     CLIENT_IP            VARCHAR(16)                    NOT NULL,
     CLIENT_USER_AGENT    VARCHAR(100)                   NULL,
     WRITE_TIME           DATETIME                       NOT NULL,
     LAST_READ_TIME       DATETIME                       NULL,
     LAST_REPLY_TIME      DATETIME                       NULL,
     MODIFY_TIME          DATETIME                       NULL,
     REPLY_NOTIFY         SMALLINT                       NOT NULL,
     DIARY_TYPE           SMALLINT                       NOT NULL,
     LOCKED               SMALLINT                       NOT NULL,
     STATUS               SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_EXTERNAL_REFER                                   */
/*==============================================================*/
CREATE TABLE DLOG_EXTERNAL_REFER (
     REFER_ID             NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NULL,
     REF_ID               INT                            NOT NULL,
     REF_TYPE             SMALLINT                       NOT NULL,
     REFER_HOST           VARCHAR(50)                    NULL,
     REFER_URL            VARCHAR(250)                   NOT NULL,
     CLIENT_IP            VARCHAR(16)                    NOT NULL,
     REFER_TIME           DATETIME                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_FCK_UPLOAD_FILE                                  */
/*==============================================================*/
CREATE TABLE DLOG_FCK_UPLOAD_FILE (
     FCK_FILE_ID          NUMERIC                        IDENTITY,
     USERID               NUMERIC                        NOT NULL,
     SITE_ID              NUMERIC                        NULL,
     UPLOAD_TIME          DATETIME                       NOT NULL,
     SESSION_ID           VARCHAR(100)                   NOT NULL,
     REF_ID               INT                            NOT NULL,
     REF_TYPE             SMALLINT                       NOT NULL,
     SAVE_PATH            VARCHAR(255)                   NOT NULL,
     FILE_URI             VARCHAR(100)                   NOT NULL,
     FILE_TYPE            INT                            NOT NULL,
     FILE_SIZE            INT                            NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_FORUM                                            */
/*==============================================================*/
CREATE TABLE DLOG_FORUM (
     FORUM_ID             NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NOT NULL,
     DLOG_TYPE_ID         NUMERIC                        NULL,
     FORUM_NAME           VARCHAR(40)                    NOT NULL,
     FORUM_DESC           VARCHAR(200)                   NULL,
     FORUM_TYPE           INT                            NOT NULL,
     CREATE_TIME          DATETIME                       NOT NULL,
     MODIFY_TIME          DATETIME                       NULL,
     LAST_TIME            DATETIME                       NULL,
     LAST_USER_ID         INT                            NULL,
     LAST_USER_NAME       VARCHAR(50)                    NULL,
     LAST_TOPIC_ID        INT                            NULL,
     SORT_ORDER           INT                            NOT NULL,
     TOPIC_COUNT          INT                            NOT NULL,
     FORUM_OPTION         INT                            NOT NULL,
     STATUS               SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_FRIEND                                           */
/*==============================================================*/
CREATE TABLE DLOG_FRIEND (
     USER_ID              INT                            NOT NULL,
     FRIEND_ID            INT                            NOT NULL,
     FRIEND_TYPE          INT                            NOT NULL,
     FRIEND_ROLE          INT                            NOT NULL,
     ADD_TIME             DATETIME                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_GUESTBOOK                                        */
/*==============================================================*/
CREATE TABLE DLOG_GUESTBOOK (
     GUEST_BOOK_ID        NUMERIC                        IDENTITY,
     USERID               NUMERIC                        NOT NULL,
     SITE_ID              NUMERIC                        NOT NULL,
     CONTENT              TEXT                           NOT NULL,
     CLIENT_TYPE          SMALLINT                       NOT NULL,
     CLIENT_IP            VARCHAR(16)                    NOT NULL,
     CLIENT_USER_AGENT    VARCHAR(100)                   NULL,
     CREATE_TIME          DATETIME                       NOT NULL,
     REPLY_CONTENT        TEXT                           NULL,
     REPLY_TIME           DATETIME                       NULL
)
go


/*==============================================================*/
/* Table: DLOG_J_REPLY                                          */
/*==============================================================*/
CREATE TABLE DLOG_J_REPLY (
     J_REPLY_ID           NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NOT NULL,
     USERID               NUMERIC                        NULL,
     DIARY_ID             NUMERIC                        NOT NULL,
     AUTHOR               VARCHAR(20)                    NOT NULL,
     AUTHOR_URL           VARCHAR(100)                   NULL,
     AUTHOR_EMAIL         VARCHAR(50)                    NULL,
     CLIENT_TYPE          SMALLINT                       NOT NULL,
     CLIENT_IP            VARCHAR(16)                    NOT NULL,
     CLIENT_USER_AGENT    VARCHAR(100)                   NULL,
     OWNER_ONLY           INT                            NOT NULL,
     CONTENT              TEXT                           NOT NULL,
     WRITE_TIME           DATETIME                       NOT NULL,
     STATUS               SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_LINK                                             */
/*==============================================================*/
CREATE TABLE DLOG_LINK (
     LINKID               NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NOT NULL,
     LINK_TITLE           VARCHAR(40)                    NOT NULL,
     LINK_URL             VARCHAR(200)                   NOT NULL,
     LINK_TYPE            SMALLINT                       NOT NULL,
     SORT_ORDER           INT                            NOT NULL,
     CREATE_TIME          DATETIME                       NOT NULL,
     STATUS               SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_MESSAGE                                          */
/*==============================================================*/
CREATE TABLE DLOG_MESSAGE (
     MSGID                NUMERIC                        IDENTITY,
     USERID               NUMERIC                        NULL,
     FROM_USER_ID         INT                            NOT NULL,
     CONTENT              TEXT                           NOT NULL,
     SEND_TIME            DATETIME                       NOT NULL,
     EXPIRE_TIME          DATETIME                       NULL,
     READ_TIME            DATETIME                       NULL,
     STATUS               SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_MUSIC                                            */
/*==============================================================*/
CREATE TABLE DLOG_MUSIC (
     MUSIC_ID             NUMERIC                        IDENTITY,
     MUSIC_BOX_ID         NUMERIC                        NULL,
     USERID               NUMERIC                        NULL,
     SITE_ID              NUMERIC                        NOT NULL,
     MUSIC_TITLE          VARCHAR(100)                   NOT NULL,
     MUSIC_WORD           TEXT                           NULL,
     ALBUM                VARCHAR(100)                   NULL,
     SINGER               VARCHAR(50)                    NULL,
     URL                  VARCHAR(200)                   NULL,
     CREATE_TIME          DATETIME                       NOT NULL,
     VIEW_COUNT           INT                            NOT NULL,
     MUSIC_TYPE           INT                            NOT NULL,
     STATUS               SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_MUSICBOX                                         */
/*==============================================================*/
CREATE TABLE DLOG_MUSICBOX (
     MUSIC_BOX_ID         NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NOT NULL,
     BOX_NAME             VARCHAR(40)                    NOT NULL,
     BOX_DESC             VARCHAR(100)                   NULL,
     MUSIC_COUNT          INT                            NOT NULL,
     CREATE_TIME          DATETIME                       NOT NULL,
     SORT_ORDER           INT                            NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_MY_BLACKLIST                                     */
/*==============================================================*/
CREATE TABLE DLOG_MY_BLACKLIST (
     MY_USER_ID           INT                            NOT NULL,
     OTHER_USER_ID        INT                            NOT NULL,
     BL_TYPE              INT                            NOT NULL,
     ADD_TIME             DATETIME                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_P_REPLY                                          */
/*==============================================================*/
CREATE TABLE DLOG_P_REPLY (
     P_REPLY_ID           NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NOT NULL,
     PHOTO_ID             NUMERIC                        NOT NULL,
     USERID               NUMERIC                        NULL,
     AUTHOR               VARCHAR(20)                    NOT NULL,
     AUTHOR_URL           VARCHAR(100)                   NULL,
     AUTHOR_EMAIL         VARCHAR(50)                    NULL,
     CLIENT_TYPE          SMALLINT                       NOT NULL,
     CLIENT_IP            VARCHAR(16)                    NOT NULL,
     CLIENT_USER_AGENT    VARCHAR(100)                   NULL,
     OWNER_ONLY           INT                            NOT NULL,
     CONTENT              TEXT                           NOT NULL,
     WRITE_TIME           DATETIME                       NOT NULL,
     STATUS               SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_PHOTO                                            */
/*==============================================================*/
CREATE TABLE DLOG_PHOTO (
     PHOTO_ID             NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NOT NULL,
     ALBUM_ID             NUMERIC                        NOT NULL,
     USERID               NUMERIC                        NOT NULL,
     PHOTO_NAME           VARCHAR(40)                    NOT NULL,
     PHOTO_DESC           TEXT                           NULL,
     FILE_NAME            VARCHAR(100)                   NOT NULL,
     PHOTO_URL            VARCHAR(100)                   NOT NULL,
     PREVIEW_URL          VARCHAR(100)                   NOT NULL,
     TAGS                 VARCHAR(100)                   NULL,
     P_YEAR               INT                            NOT NULL,
     P_MONTH              SMALLINT                       NOT NULL,
     P_DATE               SMALLINT                       NOT NULL,
     WIDTH                INT                            NOT NULL,
     HEIGHT               INT                            NOT NULL,
     PHOTO_SIZE           INT                            NOT NULL,
     COLOR_BIT            INT                            NOT NULL,
     EXIF_MANUFACTURER    VARCHAR(50)                    NULL,
     EXIF_MODEL           VARCHAR(50)                    NULL,
     EXIF_ISO             INT                            NOT NULL,
     EXIF_APERTURE        VARCHAR(20)                    NULL,
     EXIF_SHUTTER         VARCHAR(20)                    NULL,
     EXIF_EXPOSURE_BIAS   VARCHAR(20)                    NULL,
     EXIF_EXPOSURE_TIME   VARCHAR(20)                    NULL,
     EXIF_FOCAL_LENGTH    VARCHAR(20)                    NULL,
     EXIF_COLOR_SPACE     VARCHAR(20)                    NULL,
     REPLY_COUNT          INT                            NOT NULL,
     VIEW_COUNT           INT                            NOT NULL,
     CREATE_TIME          DATETIME                       NOT NULL,
     MODIFY_TIME          DATETIME                       NULL,
     LAST_REPLY_TIME      DATETIME                       NULL,
     PHOTO_TYPE           INT                            NOT NULL,
     LOCKED               SMALLINT                       NOT NULL,
     PHOTO_STATUS         SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_SITE                                             */
/*==============================================================*/
CREATE TABLE DLOG_SITE (
     SITE_ID              NUMERIC                        IDENTITY,
     USERID               NUMERIC                        NOT NULL,
     DLOG_TYPE_ID         NUMERIC                        NULL,
     SITE_NAME            VARCHAR(20)                    NOT NULL,
     SITE_C_NAME          VARCHAR(50)                    NOT NULL,
     SITE_URL             VARCHAR(100)                   NULL,
     SITE_TITLE           VARCHAR(100)                   NULL,
     SITE_DETAIL          VARCHAR(250)                   NULL,
     SITE_ICP             VARCHAR(20)                    NULL,
     SITE_LOGO            VARCHAR(50)                    NULL,
     SITE_CSS             VARCHAR(50)                    NULL,
     SITE_LAYOUT          VARCHAR(50)                    NULL,
     SITE_LANG            VARCHAR(10)                    NULL,
     SITE_FLAG            INT                            NOT NULL,
     CREATE_TIME          DATETIME                       NOT NULL,
     LAST_TIME            DATETIME                       NULL,
     EXPIRED_TIME         DATETIME                       NULL,
     LAST_EXP_TIME        DATETIME                       NULL,
     ACCESS_MODE          SMALLINT                       NOT NULL,
     ACCESS_CODE          VARCHAR(50)                    NULL,
     DIARY_STATUS         SMALLINT                       NOT NULL,
     PHOTO_STATUS         SMALLINT                       NOT NULL,
     MUSIC_STATUS         SMALLINT                       NOT NULL,
     FORUM_STATUS         SMALLINT                       NOT NULL,
     GUESTBOOK_STATUS     SMALLINT                       NOT NULL,
     DIARY_CNAME          VARCHAR(16)                    NULL,
     PHOTO_CNAME          VARCHAR(16)                    NULL,
     MUSIC_CNAME          VARCHAR(16)                    NULL,
     BBS_CNAME            VARCHAR(16)                    NULL,
     GUESTBOOK_CNAME      VARCHAR(16)                    NULL,
     PHOTO_SPACE_TOTAL    INT                            NOT NULL,
     PHOTO_SPACE_USED     INT                            NOT NULL,
     DIARY_SPACE_TOTAL    INT                            NOT NULL,
     DIARY_SPACE_USED     INT                            NOT NULL,
     MEDIA_SPACE_TOTAL    INT                            NOT NULL,
     MEDIA_SPACE_USED     INT                            NOT NULL,
     SITE_TYPE            INT                            NOT NULL,
     SITE_LEVEL           INT                            NOT NULL,
     STATUS               SMALLINT                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_SITE_STAT                                        */
/*==============================================================*/
CREATE TABLE DLOG_SITE_STAT (
     SITE_STAT_ID         NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NULL,
     STAT_DATE            INT                            NOT NULL,
     UV_COUNT             INT                            NOT NULL,
     PV_COUNT             INT                            NOT NULL,
     V_SOURCE             INT                            NOT NULL,
     UPDATE_TIME          DATETIME                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_T_REPLY                                          */
/*==============================================================*/
CREATE TABLE DLOG_T_REPLY (
     T_REPLY_ID           NUMERIC                        IDENTITY,
     TOPIC_ID             NUMERIC                        NOT NULL,
     USERID               NUMERIC                        NOT NULL,
     SITE_ID              NUMERIC                        NOT NULL,
     TITLE                VARCHAR(200)                   NOT NULL,
     CONTENT              TEXT                           NOT NULL,
     WRITE_TIME           DATETIME                       NULL,
     STATUS               SMALLINT                       NULL,
     CLIENT_IP            VARCHAR(16)                    NULL,
     CLIENT_TYPE          SMALLINT                       NULL,
     CLIENT_USER_AGENT    VARCHAR(100)                   NULL
)
go


/*==============================================================*/
/* Table: DLOG_TAG                                              */
/*==============================================================*/
CREATE TABLE DLOG_TAG (
     TAG_ID               NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NOT NULL,
     REF_ID               INT                            NOT NULL,
     REF_TYPE             SMALLINT                       NOT NULL,
     TAG_NAME             VARCHAR(20)                    NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_TOPIC                                            */
/*==============================================================*/
CREATE TABLE DLOG_TOPIC (
     TOPIC_ID             NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NOT NULL,
     USERID               NUMERIC                        NOT NULL,
     FORUM_ID             NUMERIC                        NOT NULL,
     USERNAME             VARCHAR(40)                    NOT NULL,
     CREATE_TIME          DATETIME                       NOT NULL,
     MODIFY_TIME          DATETIME                       NULL,
     TITLE                VARCHAR(200)                   NOT NULL,
     CONTENT              TEXT                           NOT NULL,
     TAGS                 VARCHAR(100)                   NULL,
     LAST_REPLY_TIME      DATETIME                       NULL,
     LAST_REPLY_ID        INT                            NULL,
     LAST_USER_ID         INT                            NULL,
     LAST_USER_NAME       VARCHAR(50)                    NULL,
     REPLY_COUNT          INT                            NOT NULL,
     VIEW_COUNT           INT                            NOT NULL,
     LOCKED               SMALLINT                       NOT NULL,
     TOPIC_TYPE           INT                            NOT NULL,
     STATUS               SMALLINT                       NOT NULL,
     CLIENT_TYPE          SMALLINT                       NOT NULL,
     CLIENT_IP            VARCHAR(16)                    NOT NULL,
     CLIENT_USER_AGENT    VARCHAR(100)                   NULL
)
go


/*==============================================================*/
/* Table: DLOG_TRACKBACK                                        */
/*==============================================================*/
CREATE TABLE DLOG_TRACKBACK (
     TRACK_ID             NUMERIC                        IDENTITY,
     PARENT_ID            INT                            NOT NULL,
     PARENT_TYPE          SMALLINT                       NOT NULL,
     REFURL               VARCHAR(100)                   NOT NULL,
     TITLE                VARCHAR(200)                   NULL,
     EXCERPT              VARCHAR(200)                   NULL,
     BLOG_NAME            VARCHAR(50)                    NOT NULL,
     REMOTE_ADDR          CHAR(15)                       NOT NULL,
     TRACK_TIME           DATETIME                       NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_TYPE                                             */
/*==============================================================*/
CREATE TABLE DLOG_TYPE (
     DLOG_TYPE_ID         NUMERIC                        IDENTITY,
     DLO_DLOG_TYPE_ID     NUMERIC                        NULL,
     TYPE_NAME            VARCHAR(20)                    NOT NULL,
     SORT_ORDER           INT                            NOT NULL
)
go


/*==============================================================*/
/* Table: DLOG_USER                                             */
/*==============================================================*/
CREATE TABLE DLOG_USER (
     USERID               NUMERIC                        IDENTITY,
     SITE_ID              NUMERIC                        NULL,
     OWN_SITE_ID          INT                            NOT NULL,
     USERNAME             VARCHAR(40)                    NOT NULL,
     PASSWORD             VARCHAR(50)                    NOT NULL,
     USER_ROLE            INT                            NOT NULL,
     NICKNAME             VARCHAR(50)                    NOT NULL,
     SEX                  SMALLINT                       NOT NULL,
     BIRTH                DATETIME                       NULL,
     EMAIL                VARCHAR(50)                    NULL,
     HOMEPAGE             VARCHAR(50)                    NULL,
     QQ                   VARCHAR(16)                    NULL,
     MSN                  VARCHAR(50)                    NULL,
     MOBILE               VARCHAR(16)                    NULL,
     NATION               VARCHAR(40)                    NULL,
     PROVINCE             VARCHAR(40)                    NULL,
     CITY                 VARCHAR(40)                    NULL,
     INDUSTRY             VARCHAR(40)                    NULL,
     COMPANY              VARCHAR(80)                    NULL,
     ADDRESS              VARCHAR(200)                   NULL,
     JOB                  VARCHAR(40)                    NULL,
     FAX                  VARCHAR(20)                    NULL,
     TEL                  VARCHAR(20)                    NULL,
     ZIP                  VARCHAR(12)                    NULL,
     PORTRAIT             VARCHAR(100)                   NULL,
     RESUME               VARCHAR(200)                   NULL,
     REGTIME              DATETIME                       NOT NULL,
     LAST_TIME            DATETIME                       NULL,
     LAST_IP              VARCHAR(16)                    NULL,
     KEEP_DAYS            INT                            NOT NULL,
     ONLINE_STATUS        INT                            NOT NULL,
     USER_LEVEL           INT                            NOT NULL,
     STATUS               SMALLINT                       NOT NULL,
     ARTICLE_COUNT        INT                            NOT NULL,
     ARTICLE_REPLY_COUNT  INT                            NOT NULL,
     TOPIC_COUNT          INT                            NOT NULL,
     TOPIC_REPLY_COUNT    INT                            NOT NULL,
     PHOTO_COUNT          INT                            NOT NULL,
     PHOTO_REPLY_COUNT    INT                            NOT NULL,
     GUESTBOOK_COUNT      INT                            NOT NULL,
     BOOKMARK_COUNT       INT                            NOT NULL
)
go


ALTER TABLE DLOG_ALBUM
   ADD CONSTRAINT FK_DLOG_ALB_R_ALBUM_DLOG_ALB FOREIGN KEY (DLO_ALBUM_ID)
      REFERENCES DLOG_ALBUM (ALBUM_ID)
go


ALTER TABLE DLOG_ALBUM
   ADD CONSTRAINT FK_DLOG_ALB_R_ALBUM_C_DLOG_PHO FOREIGN KEY (PHOTO_ID)
      REFERENCES DLOG_PHOTO (PHOTO_ID)
go


ALTER TABLE DLOG_ALBUM
   ADD CONSTRAINT FK_DLOG_ALB_R_ALBUM_T_DLOG_TYP FOREIGN KEY (DLOG_TYPE_ID)
      REFERENCES DLOG_TYPE (DLOG_TYPE_ID)
go


ALTER TABLE DLOG_ALBUM
   ADD CONSTRAINT FK_DLOG_ALB_R_SITE_AL_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_BLOCKED_IP
   ADD CONSTRAINT FK_DLOG_BLO_R_SITE_BL_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_BOOKMARK
   ADD CONSTRAINT FK_DLOG_BOO_R_SITE_BO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_BOOKMARK
   ADD CONSTRAINT FK_DLOG_BOO_R_USER_MA_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_BULLETIN
   ADD CONSTRAINT FK_DLOG_BUL_R_SITE_BU_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_CATALOG
   ADD CONSTRAINT FK_DLOG_CAT_R_CATALOG_DLOG_TYP FOREIGN KEY (DLOG_TYPE_ID)
      REFERENCES DLOG_TYPE (DLOG_TYPE_ID)
go


ALTER TABLE DLOG_CATALOG
   ADD CONSTRAINT FK_DLOG_CAT_R_SITE_CA_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_CATALOG_PERM
   ADD CONSTRAINT FK_DLOG_CAT_DLOG_CATA_DLOG_CAT FOREIGN KEY (CATALOG_ID)
      REFERENCES DLOG_CATALOG (CATALOG_ID)
go


ALTER TABLE DLOG_CATALOG_PERM
   ADD CONSTRAINT FK_DLOG_CAT_DLOG_CATA_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_COMMENTS
   ADD CONSTRAINT FK_DLOG_COM_R_SITE_CO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_COMMENTS
   ADD CONSTRAINT FK_DLOG_COM_R_SUB_COM_DLOG_COM FOREIGN KEY (DLO_COMMENT_ID)
      REFERENCES DLOG_COMMENTS (COMMENT_ID)
go


ALTER TABLE DLOG_CONFIG
   ADD CONSTRAINT FK_DLOG_CON_R_SITE_CO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_DIARY
   ADD CONSTRAINT FK_DLOG_DIA_R_CATALOG_DLOG_CAT FOREIGN KEY (CATALOG_ID)
      REFERENCES DLOG_CATALOG (CATALOG_ID)
go


ALTER TABLE DLOG_DIARY
   ADD CONSTRAINT FK_DLOG_DIA_R_SITE_JO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_DIARY
   ADD CONSTRAINT FK_DLOG_DIA_R_USER_JO_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_EXTERNAL_REFER
   ADD CONSTRAINT FK_DLOG_EXT_R_SITE_RE_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_FCK_UPLOAD_FILE
   ADD CONSTRAINT FK_DLOG_FCK_R_SITE_FI_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_FCK_UPLOAD_FILE
   ADD CONSTRAINT FK_DLOG_FCK_R_USER_UP_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_FORUM
   ADD CONSTRAINT FK_DLOG_FOR_R_FORUM_T_DLOG_TYP FOREIGN KEY (DLOG_TYPE_ID)
      REFERENCES DLOG_TYPE (DLOG_TYPE_ID)
go


ALTER TABLE DLOG_FORUM
   ADD CONSTRAINT FK_DLOG_FOR_R_SITE_FO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_GUESTBOOK
   ADD CONSTRAINT FK_DLOG_GUE_R_SITE_GU_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_GUESTBOOK
   ADD CONSTRAINT FK_DLOG_GUE_R_USER_LI_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_J_REPLY
   ADD CONSTRAINT FK_DLOG_J_R_R_JOURNAL_DLOG_DIA FOREIGN KEY (DIARY_ID)
      REFERENCES DLOG_DIARY (DIARY_ID)
go


ALTER TABLE DLOG_J_REPLY
   ADD CONSTRAINT FK_DLOG_J_R_R_SITE_J__DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_J_REPLY
   ADD CONSTRAINT FK_DLOG_J_R_R_USER_J__DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_LINK
   ADD CONSTRAINT FK_DLOG_LIN_R_SITE_LI_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_MESSAGE
   ADD CONSTRAINT FK_DLOG_MES_R_MSG_REC_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_MUSIC
   ADD CONSTRAINT FK_DLOG_MUS_R_MUSIC_B_DLOG_MUS FOREIGN KEY (MUSIC_BOX_ID)
      REFERENCES DLOG_MUSICBOX (MUSIC_BOX_ID)
go


ALTER TABLE DLOG_MUSIC
   ADD CONSTRAINT FK_DLOG_MUS_R_RECOMME_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_MUSIC
   ADD CONSTRAINT FK_DLOG_MUS_R_SITE_MU_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_MUSICBOX
   ADD CONSTRAINT FK_DLOG_MUS_R_SITE_MB_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_P_REPLY
   ADD CONSTRAINT FK_DLOG_P_R_R_PHOTO_R_DLOG_PHO FOREIGN KEY (PHOTO_ID)
      REFERENCES DLOG_PHOTO (PHOTO_ID)
go


ALTER TABLE DLOG_P_REPLY
   ADD CONSTRAINT FK_DLOG_P_R_R_SITE_P__DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_P_REPLY
   ADD CONSTRAINT FK_DLOG_P_R_R_USER_P__DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_PHOTO
   ADD CONSTRAINT FK_DLOG_PHO_R_ALBUM_P_DLOG_ALB FOREIGN KEY (ALBUM_ID)
      REFERENCES DLOG_ALBUM (ALBUM_ID)
go


ALTER TABLE DLOG_PHOTO
   ADD CONSTRAINT FK_DLOG_PHO_R_PHOTO_O_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_PHOTO
   ADD CONSTRAINT FK_DLOG_PHO_R_SITE_PH_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_SITE
   ADD CONSTRAINT FK_DLOG_SIT_R_SITE_TY_DLOG_TYP FOREIGN KEY (DLOG_TYPE_ID)
      REFERENCES DLOG_TYPE (DLOG_TYPE_ID)
go


ALTER TABLE DLOG_SITE
   ADD CONSTRAINT FK_DLOG_SIT_R_USER_SI_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_SITE_STAT
   ADD CONSTRAINT FK_DLOG_SIT_R_SITE_ST_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_T_REPLY
   ADD CONSTRAINT FK_DLOG_T_R_R_SITE_T__DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_T_REPLY
   ADD CONSTRAINT FK_DLOG_T_R_R_TOPIC_R_DLOG_TOP FOREIGN KEY (TOPIC_ID)
      REFERENCES DLOG_TOPIC (TOPIC_ID)
go


ALTER TABLE DLOG_T_REPLY
   ADD CONSTRAINT FK_DLOG_T_R_R_USER_T__DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_TAG
   ADD CONSTRAINT FK_DLOG_TAG_R_SITE_TA_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_TOPIC
   ADD CONSTRAINT FK_DLOG_TOP_R_FORUM_T_DLOG_FOR FOREIGN KEY (FORUM_ID)
      REFERENCES DLOG_FORUM (FORUM_ID)
go


ALTER TABLE DLOG_TOPIC
   ADD CONSTRAINT FK_DLOG_TOP_R_SITE_TO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


ALTER TABLE DLOG_TOPIC
   ADD CONSTRAINT FK_DLOG_TOP_R_USER_TO_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
go


ALTER TABLE DLOG_TYPE
   ADD CONSTRAINT FK_DLOG_TYP_R_SITE_TY_DLOG_TYP FOREIGN KEY (DLO_DLOG_TYPE_ID)
      REFERENCES DLOG_TYPE (DLOG_TYPE_ID)
go


ALTER TABLE DLOG_USER
   ADD CONSTRAINT FK_DLOG_USE_R_SITE_US_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
go


