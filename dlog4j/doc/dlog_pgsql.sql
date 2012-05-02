/*==============================================================*/
/* DBMS name:      PostgreSQL 7.3                               */
/* Created on:     2006-9-4 23:27:35                            */
/*==============================================================*/


/*==============================================================*/
/* Table: DLOG_ALBUM                                            */
/*==============================================================*/
CREATE TABLE DLOG_ALBUM (
ALBUM_ID             SERIAL               NOT NULL,
PHOTO_ID             INT4                 NULL,
DLO_ALBUM_ID         INT4                 NULL,
SITE_ID              INT4                 NOT NULL,
DLOG_TYPE_ID         INT4                 NULL,
ALBUM_NAME           VARCHAR(40)          NOT NULL,
ALBUM_DESC           VARCHAR(200)         NULL,
PHOTO_COUNT          INT4                 NOT NULL,
ALBUM_TYPE           INT4                 NOT NULL,
VERIFYCODE           VARCHAR(20)          NULL,
SORT_ORDER           INT4                 NOT NULL,
CREATE_TIME          DATE                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_BLOCKED_IP                                       */
/*==============================================================*/
CREATE TABLE DLOG_BLOCKED_IP (
BLOCKED_IP_ID        SERIAL               NOT NULL,
SITE_ID              INT4                 NULL,
IP_ADDR              INT4                 NOT NULL,
S_IP_ADDR            VARCHAR(16)          NOT NULL,
IP_MASK              INT4                 NOT NULL,
S_IP_MASK            VARCHAR(16)          NOT NULL,
BLOCKED_TYPE         INT2                 NOT NULL,
BLOCKED_TIME         DATE                 NOT NULL,
STATUS               INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_BOOKMARK                                         */
/*==============================================================*/
CREATE TABLE DLOG_BOOKMARK (
MARK_ID              SERIAL               NOT NULL,
USERID               INT4                 NOT NULL,
SITE_ID              INT4                 NOT NULL,
PARENT_ID            INT4                 NOT NULL,
PARENT_TYPE          INT2                 NOT NULL,
TITLE                VARCHAR(200)         NULL,
URL                  VARCHAR(200)         NULL,
CREATE_TIME          DATE                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_BULLETIN                                         */
/*==============================================================*/
CREATE TABLE DLOG_BULLETIN (
BULLETIN_ID          SERIAL               NOT NULL,
SITE_ID              INT4                 NOT NULL,
BULLETIN_TYPE        INT4                 NOT NULL,
PUB_TIME             DATE                 NOT NULL,
STATUS               INT2                 NOT NULL,
TITLE                VARCHAR(200)         NOT NULL,
CONTENT              TEXT                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_CATALOG                                          */
/*==============================================================*/
CREATE TABLE DLOG_CATALOG (
CATALOG_ID           SERIAL               NOT NULL,
DLOG_TYPE_ID         INT4                 NULL,
SITE_ID              INT4                 NOT NULL,
CATALOG_NAME         VARCHAR(20)          NOT NULL,
CATALOG_DESC         VARCHAR(200)         NULL,
CREATE_TIME          DATE                 NOT NULL,
ARTICLE_COUNT        INT4                 NOT NULL,
CATALOG_TYPE         INT2                 NOT NULL,
VERIFYCODE           VARCHAR(20)          NULL,
SORT_ORDER           INT4                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_CATALOG_PERM                                     */
/*==============================================================*/
CREATE TABLE DLOG_CATALOG_PERM (
CATALOG_ID           INT4                 NOT NULL,
USERID               INT4                 NOT NULL,
USER_ROLE            INT4                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_COMMENTS                                         */
/*==============================================================*/
CREATE TABLE DLOG_COMMENTS (
COMMENT_ID           SERIAL               NOT NULL,
DLO_COMMENT_ID       INT4                 NULL,
SITE_ID              INT4                 NULL,
ENTITY_ID            INT4                 NOT NULL,
ENTITY_TYPE          INT4                 NOT NULL,
CLIENT_IP            VARCHAR(16)          NOT NULL,
CLIENT_TYPE          INT2                 NOT NULL,
CLIENT_USER_AGENT    VARCHAR(100)         NULL,
AUTHOR_ID            INT4                 NOT NULL,
AUTHOR               VARCHAR(20)          NOT NULL,
AUTHOR_EMAIL         VARCHAR(50)          NULL,
AUTHOR_URL           VARCHAR(100)         NULL,
TITLE                VARCHAR(200)         NOT NULL,
CONTENT              TEXT                 NOT NULL,
CONTENT_FORMAT       INT2                 NOT NULL,
COMMENT_TIME         DATE                 NOT NULL,
COMMENT_FLAG         INT2                 NOT NULL,
COMMENT_STATUS       INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_CONFIG                                           */
/*==============================================================*/
CREATE TABLE DLOG_CONFIG (
CONFIG_ID            SERIAL               NOT NULL,
SITE_ID              INT4                 NULL,
CONFIG_NAME          VARCHAR(20)          NOT NULL,
INT_VALUE            INT4                 NULL,
STRING_VALUE         VARCHAR(100)         NULL,
DATE_VALUE           DATE                 NULL,
TIME_VALUE           TIME                 NULL,
TIMESTAMP_VALUE      DATE                 NULL,
LAST_UPDATE          DATE                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_DIARY                                            */
/*==============================================================*/
CREATE TABLE DLOG_DIARY (
DIARY_ID             SERIAL               NOT NULL,
USERID               INT4                 NOT NULL,
SITE_ID              INT4                 NOT NULL,
CATALOG_ID           INT4                 NOT NULL,
AUTHOR               VARCHAR(20)          NOT NULL,
AUTHOR_URL           VARCHAR(100)         NULL,
TITLE                VARCHAR(200)         NOT NULL,
CONTENT              TEXT                 NOT NULL,
DIARY_SIZE           INT4                 NOT NULL,
REFER                VARCHAR(100)         NULL,
WEATHER              VARCHAR(20)          NOT NULL,
MOOD_LEVEL           INT2                 NOT NULL,
TAGS                 VARCHAR(100)         NULL,
BGSOUND              INT4                 NULL,
REPLY_COUNT          INT4                 NOT NULL,
VIEW_COUNT           INT4                 NOT NULL,
TB_COUNT             INT4                 NOT NULL,
CLIENT_TYPE          INT2                 NOT NULL,
CLIENT_IP            VARCHAR(16)          NOT NULL,
CLIENT_USER_AGENT    VARCHAR(100)         NULL,
WRITE_TIME           DATE                 NOT NULL,
LAST_READ_TIME       DATE                 NULL,
LAST_REPLY_TIME      DATE                 NULL,
MODIFY_TIME          DATE                 NULL,
REPLY_NOTIFY         INT2                 NOT NULL,
DIARY_TYPE           INT2                 NOT NULL,
LOCKED               INT2                 NOT NULL,
STATUS               INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_EXTERNAL_REFER                                   */
/*==============================================================*/
CREATE TABLE DLOG_EXTERNAL_REFER (
REFER_ID             SERIAL               NOT NULL,
SITE_ID              INT4                 NULL,
REF_ID               INT4                 NOT NULL,
REF_TYPE             INT2                 NOT NULL,
REFER_HOST           VARCHAR(50)          NULL,
REFER_URL            VARCHAR(250)         NOT NULL,
CLIENT_IP            VARCHAR(16)          NOT NULL,
REFER_TIME           DATE                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_FCK_UPLOAD_FILE                                  */
/*==============================================================*/
CREATE TABLE DLOG_FCK_UPLOAD_FILE (
FCK_FILE_ID          SERIAL               NOT NULL,
USERID               INT4                 NOT NULL,
SITE_ID              INT4                 NULL,
UPLOAD_TIME          DATE                 NOT NULL,
SESSION_ID           VARCHAR(100)         NOT NULL,
REF_ID               INT4                 NOT NULL,
REF_TYPE             INT2                 NOT NULL,
SAVE_PATH            VARCHAR(255)         NOT NULL,
FILE_URI             VARCHAR(100)         NOT NULL,
FILE_TYPE            INT4                 NOT NULL,
FILE_SIZE            INT4                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_FORUM                                            */
/*==============================================================*/
CREATE TABLE DLOG_FORUM (
FORUM_ID             SERIAL               NOT NULL,
SITE_ID              INT4                 NOT NULL,
DLOG_TYPE_ID         INT4                 NULL,
FORUM_NAME           VARCHAR(40)          NOT NULL,
FORUM_DESC           VARCHAR(200)         NULL,
FORUM_TYPE           INT4                 NOT NULL,
CREATE_TIME          DATE                 NOT NULL,
MODIFY_TIME          DATE                 NULL,
LAST_TIME            DATE                 NULL,
LAST_USER_ID         INT4                 NULL,
LAST_USER_NAME       VARCHAR(50)          NULL,
LAST_TOPIC_ID        INT4                 NULL,
SORT_ORDER           INT4                 NOT NULL,
TOPIC_COUNT          INT4                 NOT NULL,
FORUM_OPTION         INT4                 NOT NULL,
STATUS               INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_FRIEND                                           */
/*==============================================================*/
CREATE TABLE DLOG_FRIEND (
USER_ID              INT4                 NOT NULL,
FRIEND_ID            INT4                 NOT NULL,
FRIEND_TYPE          INT4                 NOT NULL,
FRIEND_ROLE          INT4                 NOT NULL,
ADD_TIME             DATE                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_GUESTBOOK                                        */
/*==============================================================*/
CREATE TABLE DLOG_GUESTBOOK (
GUEST_BOOK_ID        SERIAL               NOT NULL,
USERID               INT4                 NOT NULL,
SITE_ID              INT4                 NOT NULL,
CONTENT              TEXT                 NOT NULL,
CLIENT_TYPE          INT2                 NOT NULL,
CLIENT_IP            VARCHAR(16)          NOT NULL,
CLIENT_USER_AGENT    VARCHAR(100)         NULL,
CREATE_TIME          DATE                 NOT NULL,
REPLY_CONTENT        TEXT                 NULL,
REPLY_TIME           DATE                 NULL
);

/*==============================================================*/
/* Table: DLOG_J_REPLY                                          */
/*==============================================================*/
CREATE TABLE DLOG_J_REPLY (
J_REPLY_ID           SERIAL               NOT NULL,
SITE_ID              INT4                 NOT NULL,
USERID               INT4                 NULL,
DIARY_ID             INT4                 NOT NULL,
AUTHOR               VARCHAR(20)          NOT NULL,
AUTHOR_URL           VARCHAR(100)         NULL,
AUTHOR_EMAIL         VARCHAR(50)          NULL,
CLIENT_TYPE          INT2                 NOT NULL,
CLIENT_IP            VARCHAR(16)          NOT NULL,
CLIENT_USER_AGENT    VARCHAR(100)         NULL,
OWNER_ONLY           INT4                 NOT NULL,
CONTENT              TEXT                 NOT NULL,
WRITE_TIME           DATE                 NOT NULL,
STATUS               INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_LINK                                             */
/*==============================================================*/
CREATE TABLE DLOG_LINK (
LINKID               SERIAL               NOT NULL,
SITE_ID              INT4                 NOT NULL,
LINK_TITLE           VARCHAR(40)          NOT NULL,
LINK_URL             VARCHAR(200)         NOT NULL,
LINK_TYPE            INT2                 NOT NULL,
SORT_ORDER           INT4                 NOT NULL,
CREATE_TIME          DATE                 NOT NULL,
STATUS               INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_MESSAGE                                          */
/*==============================================================*/
CREATE TABLE DLOG_MESSAGE (
MSGID                SERIAL               NOT NULL,
USERID               INT4                 NULL,
FROM_USER_ID         INT4                 NOT NULL,
CONTENT              TEXT                 NOT NULL,
SEND_TIME            DATE                 NOT NULL,
EXPIRE_TIME          DATE                 NULL,
READ_TIME            DATE                 NULL,
STATUS               INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_MUSIC                                            */
/*==============================================================*/
CREATE TABLE DLOG_MUSIC (
MUSIC_ID             SERIAL               NOT NULL,
MUSIC_BOX_ID         INT4                 NULL,
USERID               INT4                 NULL,
SITE_ID              INT4                 NOT NULL,
MUSIC_TITLE          VARCHAR(100)         NOT NULL,
MUSIC_WORD           TEXT                 NULL,
ALBUM                VARCHAR(100)         NULL,
SINGER               VARCHAR(50)          NULL,
URL                  VARCHAR(200)         NULL,
CREATE_TIME          DATE                 NOT NULL,
VIEW_COUNT           INT4                 NOT NULL,
MUSIC_TYPE           INT4                 NOT NULL,
STATUS               INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_MUSICBOX                                         */
/*==============================================================*/
CREATE TABLE DLOG_MUSICBOX (
MUSIC_BOX_ID         SERIAL               NOT NULL,
SITE_ID              INT4                 NOT NULL,
BOX_NAME             VARCHAR(40)          NOT NULL,
BOX_DESC             VARCHAR(100)         NULL,
MUSIC_COUNT          INT4                 NOT NULL,
CREATE_TIME          DATE                 NOT NULL,
SORT_ORDER           INT4                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_MY_BLACKLIST                                     */
/*==============================================================*/
CREATE TABLE DLOG_MY_BLACKLIST (
MY_USER_ID           INT4                 NOT NULL,
OTHER_USER_ID        INT4                 NOT NULL,
BL_TYPE              INT4                 NOT NULL,
ADD_TIME             DATE                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_P_REPLY                                          */
/*==============================================================*/
CREATE TABLE DLOG_P_REPLY (
P_REPLY_ID           SERIAL               NOT NULL,
SITE_ID              INT4                 NOT NULL,
PHOTO_ID             INT4                 NOT NULL,
USERID               INT4                 NULL,
AUTHOR               VARCHAR(20)          NOT NULL,
AUTHOR_URL           VARCHAR(100)         NULL,
AUTHOR_EMAIL         VARCHAR(50)          NULL,
CLIENT_TYPE          INT2                 NOT NULL,
CLIENT_IP            VARCHAR(16)          NOT NULL,
CLIENT_USER_AGENT    VARCHAR(100)         NULL,
OWNER_ONLY           INT4                 NOT NULL,
CONTENT              TEXT                 NOT NULL,
WRITE_TIME           DATE                 NOT NULL,
STATUS               INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_PHOTO                                            */
/*==============================================================*/
CREATE TABLE DLOG_PHOTO (
PHOTO_ID             SERIAL               NOT NULL,
SITE_ID              INT4                 NOT NULL,
ALBUM_ID             INT4                 NOT NULL,
USERID               INT4                 NOT NULL,
PHOTO_NAME           VARCHAR(40)          NOT NULL,
PHOTO_DESC           TEXT                 NULL,
FILE_NAME            VARCHAR(100)         NOT NULL,
PHOTO_URL            VARCHAR(100)         NOT NULL,
PREVIEW_URL          VARCHAR(100)         NOT NULL,
TAGS                 VARCHAR(100)         NULL,
P_YEAR               INT4                 NOT NULL,
P_MONTH              INT2                 NOT NULL,
P_DATE               INT2                 NOT NULL,
WIDTH                INT4                 NOT NULL,
HEIGHT               INT4                 NOT NULL,
PHOTO_SIZE           INT8                 NOT NULL,
COLOR_BIT            INT4                 NOT NULL,
EXIF_MANUFACTURER    VARCHAR(50)          NULL,
EXIF_MODEL           VARCHAR(50)          NULL,
EXIF_ISO             INT4                 NOT NULL,
EXIF_APERTURE        VARCHAR(20)          NULL,
EXIF_SHUTTER         VARCHAR(20)          NULL,
EXIF_EXPOSURE_BIAS   VARCHAR(20)          NULL,
EXIF_EXPOSURE_TIME   VARCHAR(20)          NULL,
EXIF_FOCAL_LENGTH    VARCHAR(20)          NULL,
EXIF_COLOR_SPACE     VARCHAR(20)          NULL,
REPLY_COUNT          INT4                 NOT NULL,
VIEW_COUNT           INT4                 NOT NULL,
CREATE_TIME          DATE                 NOT NULL,
MODIFY_TIME          DATE                 NULL,
LAST_REPLY_TIME      DATE                 NULL,
PHOTO_TYPE           INT4                 NOT NULL,
LOCKED               INT2                 NOT NULL,
PHOTO_STATUS         INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_SITE                                             */
/*==============================================================*/
CREATE TABLE DLOG_SITE (
SITE_ID              SERIAL               NOT NULL,
USERID               INT4                 NOT NULL,
DLOG_TYPE_ID         INT4                 NULL,
SITE_NAME            VARCHAR(20)          NOT NULL,
SITE_C_NAME          VARCHAR(50)          NOT NULL,
SITE_URL             VARCHAR(100)         NULL,
SITE_TITLE           VARCHAR(100)         NULL,
SITE_DETAIL          VARCHAR(250)         NULL,
SITE_ICP             VARCHAR(20)          NULL,
SITE_LOGO            VARCHAR(50)          NULL,
SITE_CSS             VARCHAR(50)          NULL,
SITE_LAYOUT          VARCHAR(50)          NULL,
SITE_LANG            VARCHAR(10)          NULL,
SITE_FLAG            INT4                 NOT NULL,
CREATE_TIME          DATE                 NOT NULL,
LAST_TIME            DATE                 NULL,
EXPIRED_TIME         DATE                 NULL,
LAST_EXP_TIME        DATE                 NULL,
ACCESS_MODE          INT2                 NOT NULL,
ACCESS_CODE          VARCHAR(50)          NULL,
DIARY_STATUS         INT2                 NOT NULL,
PHOTO_STATUS         INT2                 NOT NULL,
MUSIC_STATUS         INT2                 NOT NULL,
FORUM_STATUS         INT2                 NOT NULL,
GUESTBOOK_STATUS     INT2                 NOT NULL,
DIARY_CNAME          VARCHAR(16)          NULL,
PHOTO_CNAME          VARCHAR(16)          NULL,
MUSIC_CNAME          VARCHAR(16)          NULL,
BBS_CNAME            VARCHAR(16)          NULL,
GUESTBOOK_CNAME      VARCHAR(16)          NULL,
PHOTO_SPACE_TOTAL    INT4                 NOT NULL,
PHOTO_SPACE_USED     INT4                 NOT NULL,
DIARY_SPACE_TOTAL    INT4                 NOT NULL,
DIARY_SPACE_USED     INT4                 NOT NULL,
MEDIA_SPACE_TOTAL    INT4                 NOT NULL,
MEDIA_SPACE_USED     INT4                 NOT NULL,
SITE_TYPE            INT4                 NOT NULL,
SITE_LEVEL           INT4                 NOT NULL,
STATUS               INT2                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_SITE_STAT                                        */
/*==============================================================*/
CREATE TABLE DLOG_SITE_STAT (
SITE_STAT_ID         SERIAL               NOT NULL,
SITE_ID              INT4                 NULL,
STAT_DATE            INT4                 NOT NULL,
UV_COUNT             INT4                 NOT NULL,
PV_COUNT             INT4                 NOT NULL,
V_SOURCE             INT4                 NOT NULL,
UPDATE_TIME          DATE                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_T_REPLY                                          */
/*==============================================================*/
CREATE TABLE DLOG_T_REPLY (
T_REPLY_ID           SERIAL               NOT NULL,
TOPIC_ID             INT4                 NOT NULL,
USERID               INT4                 NOT NULL,
SITE_ID              INT4                 NOT NULL,
TITLE                VARCHAR(200)         NOT NULL,
CONTENT              TEXT                 NOT NULL,
WRITE_TIME           DATE                 NULL,
STATUS               INT2                 NULL,
CLIENT_IP            VARCHAR(16)          NULL,
CLIENT_TYPE          INT2                 NULL,
CLIENT_USER_AGENT    VARCHAR(100)         NULL
);

/*==============================================================*/
/* Table: DLOG_TAG                                              */
/*==============================================================*/
CREATE TABLE DLOG_TAG (
TAG_ID               SERIAL               NOT NULL,
SITE_ID              INT4                 NOT NULL,
REF_ID               INT4                 NOT NULL,
REF_TYPE             INT2                 NOT NULL,
TAG_NAME             VARCHAR(20)          NOT NULL
);

/*==============================================================*/
/* Table: DLOG_TOPIC                                            */
/*==============================================================*/
CREATE TABLE DLOG_TOPIC (
TOPIC_ID             SERIAL               NOT NULL,
SITE_ID              INT4                 NOT NULL,
USERID               INT4                 NOT NULL,
FORUM_ID             INT4                 NOT NULL,
USERNAME             VARCHAR(40)          NOT NULL,
CREATE_TIME          DATE                 NOT NULL,
MODIFY_TIME          DATE                 NULL,
TITLE                VARCHAR(200)         NOT NULL,
CONTENT              TEXT                 NOT NULL,
TAGS                 VARCHAR(100)         NULL,
LAST_REPLY_TIME      DATE                 NULL,
LAST_REPLY_ID        INT4                 NULL,
LAST_USER_ID         INT4                 NULL,
LAST_USER_NAME       VARCHAR(50)          NULL,
REPLY_COUNT          INT4                 NOT NULL,
VIEW_COUNT           INT4                 NOT NULL,
LOCKED               INT2                 NOT NULL,
TOPIC_TYPE           INT4                 NOT NULL,
STATUS               INT2                 NOT NULL,
CLIENT_TYPE          INT2                 NOT NULL,
CLIENT_IP            VARCHAR(16)          NOT NULL,
CLIENT_USER_AGENT    VARCHAR(100)         NULL
);

/*==============================================================*/
/* Table: DLOG_TRACKBACK                                        */
/*==============================================================*/
CREATE TABLE DLOG_TRACKBACK (
TRACK_ID             SERIAL               NOT NULL,
PARENT_ID            INT4                 NOT NULL,
PARENT_TYPE          INT2                 NOT NULL,
REFURL               VARCHAR(100)         NOT NULL,
TITLE                VARCHAR(200)         NULL,
EXCERPT              VARCHAR(200)         NULL,
BLOG_NAME            VARCHAR(50)          NOT NULL,
REMOTE_ADDR          CHAR(15)             NOT NULL,
TRACK_TIME           DATE                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_TYPE                                             */
/*==============================================================*/
CREATE TABLE DLOG_TYPE (
DLOG_TYPE_ID         SERIAL               NOT NULL,
DLO_DLOG_TYPE_ID     INT4                 NULL,
TYPE_NAME            VARCHAR(20)          NOT NULL,
SORT_ORDER           INT4                 NOT NULL
);

/*==============================================================*/
/* Table: DLOG_USER                                             */
/*==============================================================*/
CREATE TABLE DLOG_USER (
USERID               SERIAL               NOT NULL,
SITE_ID              INT4                 NULL,
OWN_SITE_ID          INT4                 NOT NULL,
USERNAME             VARCHAR(40)          NOT NULL,
PASSWORD             VARCHAR(50)          NOT NULL,
USER_ROLE            INT4                 NOT NULL,
NICKNAME             VARCHAR(50)          NOT NULL,
SEX                  INT2                 NOT NULL,
BIRTH                DATE                 NULL,
EMAIL                VARCHAR(50)          NULL,
HOMEPAGE             VARCHAR(50)          NULL,
QQ                   VARCHAR(16)          NULL,
MSN                  VARCHAR(50)          NULL,
MOBILE               VARCHAR(16)          NULL,
NATION               VARCHAR(40)          NULL,
PROVINCE             VARCHAR(40)          NULL,
CITY                 VARCHAR(40)          NULL,
INDUSTRY             VARCHAR(40)          NULL,
COMPANY              VARCHAR(80)          NULL,
ADDRESS              VARCHAR(200)         NULL,
JOB                  VARCHAR(40)          NULL,
FAX                  VARCHAR(20)          NULL,
TEL                  VARCHAR(20)          NULL,
ZIP                  VARCHAR(12)          NULL,
PORTRAIT             VARCHAR(100)         NULL,
RESUME               VARCHAR(200)         NULL,
REGTIME              DATE                 NOT NULL,
LAST_TIME            DATE                 NULL,
LAST_IP              VARCHAR(16)          NULL,
KEEP_DAYS            INT4                 NOT NULL,
ONLINE_STATUS        INT4                 NOT NULL,
USER_LEVEL           INT4                 NOT NULL,
STATUS               INT2                 NOT NULL,
ARTICLE_COUNT        INT4                 NOT NULL,
ARTICLE_REPLY_COUNT  INT4                 NOT NULL,
TOPIC_COUNT          INT4                 NOT NULL,
TOPIC_REPLY_COUNT    INT4                 NOT NULL,
PHOTO_COUNT          INT4                 NOT NULL,
PHOTO_REPLY_COUNT    INT4                 NOT NULL,
GUESTBOOK_COUNT      INT4                 NOT NULL,
BOOKMARK_COUNT       INT4                 NOT NULL
);

ALTER TABLE DLOG_ALBUM
   ADD CONSTRAINT FK_DLOG_ALB_R_ALBUM_DLOG_ALB FOREIGN KEY (DLO_ALBUM_ID)
      REFERENCES DLOG_ALBUM (ALBUM_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_ALBUM
   ADD CONSTRAINT FK_DLOG_ALB_R_ALBUM_C_DLOG_PHO FOREIGN KEY (PHOTO_ID)
      REFERENCES DLOG_PHOTO (PHOTO_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_ALBUM
   ADD CONSTRAINT FK_DLOG_ALB_R_ALBUM_T_DLOG_TYP FOREIGN KEY (DLOG_TYPE_ID)
      REFERENCES DLOG_TYPE (DLOG_TYPE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_ALBUM
   ADD CONSTRAINT FK_DLOG_ALB_R_SITE_AL_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_BLOCKED_IP
   ADD CONSTRAINT FK_DLOG_BLO_R_SITE_BL_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_BOOKMARK
   ADD CONSTRAINT FK_DLOG_BOO_R_SITE_BO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_BOOKMARK
   ADD CONSTRAINT FK_DLOG_BOO_R_USER_MA_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_BULLETIN
   ADD CONSTRAINT FK_DLOG_BUL_R_SITE_BU_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_CATALOG
   ADD CONSTRAINT FK_DLOG_CAT_R_CATALOG_DLOG_TYP FOREIGN KEY (DLOG_TYPE_ID)
      REFERENCES DLOG_TYPE (DLOG_TYPE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_CATALOG
   ADD CONSTRAINT FK_DLOG_CAT_R_SITE_CA_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_CATALOG_PERM
   ADD CONSTRAINT FK_DLOG_CAT_DLOG_CATA_DLOG_CAT FOREIGN KEY (CATALOG_ID)
      REFERENCES DLOG_CATALOG (CATALOG_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_CATALOG_PERM
   ADD CONSTRAINT FK_DLOG_CAT_DLOG_CATA_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_COMMENTS
   ADD CONSTRAINT FK_DLOG_COM_R_SITE_CO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_COMMENTS
   ADD CONSTRAINT FK_DLOG_COM_R_SUB_COM_DLOG_COM FOREIGN KEY (DLO_COMMENT_ID)
      REFERENCES DLOG_COMMENTS (COMMENT_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_CONFIG
   ADD CONSTRAINT FK_DLOG_CON_R_SITE_CO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_DIARY
   ADD CONSTRAINT FK_DLOG_DIA_R_CATALOG_DLOG_CAT FOREIGN KEY (CATALOG_ID)
      REFERENCES DLOG_CATALOG (CATALOG_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_DIARY
   ADD CONSTRAINT FK_DLOG_DIA_R_SITE_JO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_DIARY
   ADD CONSTRAINT FK_DLOG_DIA_R_USER_JO_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_EXTERNAL_REFER
   ADD CONSTRAINT FK_DLOG_EXT_R_SITE_RE_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_FCK_UPLOAD_FILE
   ADD CONSTRAINT FK_DLOG_FCK_R_SITE_FI_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_FCK_UPLOAD_FILE
   ADD CONSTRAINT FK_DLOG_FCK_R_USER_UP_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_FORUM
   ADD CONSTRAINT FK_DLOG_FOR_R_FORUM_T_DLOG_TYP FOREIGN KEY (DLOG_TYPE_ID)
      REFERENCES DLOG_TYPE (DLOG_TYPE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_FORUM
   ADD CONSTRAINT FK_DLOG_FOR_R_SITE_FO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_GUESTBOOK
   ADD CONSTRAINT FK_DLOG_GUE_R_SITE_GU_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_GUESTBOOK
   ADD CONSTRAINT FK_DLOG_GUE_R_USER_LI_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_J_REPLY
   ADD CONSTRAINT FK_DLOG_J_R_R_JOURNAL_DLOG_DIA FOREIGN KEY (DIARY_ID)
      REFERENCES DLOG_DIARY (DIARY_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_J_REPLY
   ADD CONSTRAINT FK_DLOG_J_R_R_SITE_J__DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_J_REPLY
   ADD CONSTRAINT FK_DLOG_J_R_R_USER_J__DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_LINK
   ADD CONSTRAINT FK_DLOG_LIN_R_SITE_LI_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_MESSAGE
   ADD CONSTRAINT FK_DLOG_MES_R_MSG_REC_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_MUSIC
   ADD CONSTRAINT FK_DLOG_MUS_R_MUSIC_B_DLOG_MUS FOREIGN KEY (MUSIC_BOX_ID)
      REFERENCES DLOG_MUSICBOX (MUSIC_BOX_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_MUSIC
   ADD CONSTRAINT FK_DLOG_MUS_R_RECOMME_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_MUSIC
   ADD CONSTRAINT FK_DLOG_MUS_R_SITE_MU_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_MUSICBOX
   ADD CONSTRAINT FK_DLOG_MUS_R_SITE_MB_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_P_REPLY
   ADD CONSTRAINT FK_DLOG_P_R_R_PHOTO_R_DLOG_PHO FOREIGN KEY (PHOTO_ID)
      REFERENCES DLOG_PHOTO (PHOTO_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_P_REPLY
   ADD CONSTRAINT FK_DLOG_P_R_R_SITE_P__DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_P_REPLY
   ADD CONSTRAINT FK_DLOG_P_R_R_USER_P__DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_PHOTO
   ADD CONSTRAINT FK_DLOG_PHO_R_ALBUM_P_DLOG_ALB FOREIGN KEY (ALBUM_ID)
      REFERENCES DLOG_ALBUM (ALBUM_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_PHOTO
   ADD CONSTRAINT FK_DLOG_PHO_R_PHOTO_O_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_PHOTO
   ADD CONSTRAINT FK_DLOG_PHO_R_SITE_PH_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_SITE
   ADD CONSTRAINT FK_DLOG_SIT_R_SITE_TY_DLOG_TYP FOREIGN KEY (DLOG_TYPE_ID)
      REFERENCES DLOG_TYPE (DLOG_TYPE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_SITE
   ADD CONSTRAINT FK_DLOG_SIT_R_USER_SI_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_SITE_STAT
   ADD CONSTRAINT FK_DLOG_SIT_R_SITE_ST_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_T_REPLY
   ADD CONSTRAINT FK_DLOG_T_R_R_SITE_T__DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_T_REPLY
   ADD CONSTRAINT FK_DLOG_T_R_R_TOPIC_R_DLOG_TOP FOREIGN KEY (TOPIC_ID)
      REFERENCES DLOG_TOPIC (TOPIC_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_T_REPLY
   ADD CONSTRAINT FK_DLOG_T_R_R_USER_T__DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_TAG
   ADD CONSTRAINT FK_DLOG_TAG_R_SITE_TA_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_TOPIC
   ADD CONSTRAINT FK_DLOG_TOP_R_FORUM_T_DLOG_FOR FOREIGN KEY (FORUM_ID)
      REFERENCES DLOG_FORUM (FORUM_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_TOPIC
   ADD CONSTRAINT FK_DLOG_TOP_R_SITE_TO_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_TOPIC
   ADD CONSTRAINT FK_DLOG_TOP_R_USER_TO_DLOG_USE FOREIGN KEY (USERID)
      REFERENCES DLOG_USER (USERID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_TYPE
   ADD CONSTRAINT FK_DLOG_TYP_R_SITE_TY_DLOG_TYP FOREIGN KEY (DLO_DLOG_TYPE_ID)
      REFERENCES DLOG_TYPE (DLOG_TYPE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DLOG_USER
   ADD CONSTRAINT FK_DLOG_USE_R_SITE_US_DLOG_SIT FOREIGN KEY (SITE_ID)
      REFERENCES DLOG_SITE (SITE_ID)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

