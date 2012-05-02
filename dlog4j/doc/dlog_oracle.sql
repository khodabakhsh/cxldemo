/*==============================================================*/
/* DBMS name:      ORACLE Version 9i2                           */
/* Created on:     2006-8-13 7:40:31                            */
/*==============================================================*/


CREATE SEQUENCE "SEQ_DLOG_ALBUM";

CREATE SEQUENCE "SEQ_DLOG_BLOCKED_IP";

CREATE SEQUENCE "SEQ_DLOG_BOOKMARK";

CREATE SEQUENCE "SEQ_DLOG_BULLETIN";

CREATE SEQUENCE "SEQ_DLOG_CATALOG";

CREATE SEQUENCE "SEQ_DLOG_COMMENTS";

CREATE SEQUENCE "SEQ_DLOG_CONFIG";

CREATE SEQUENCE "SEQ_DLOG_DIARY";

CREATE SEQUENCE "SEQ_DLOG_EXTERNAL_REFER";

CREATE SEQUENCE "SEQ_DLOG_FCK_UPLOAD_FILE";

CREATE SEQUENCE "SEQ_DLOG_FORUM";

CREATE SEQUENCE "SEQ_DLOG_GUESTBOOK";

CREATE SEQUENCE "SEQ_DLOG_J_REPLY";

CREATE SEQUENCE "SEQ_DLOG_LINK";

CREATE SEQUENCE "SEQ_DLOG_MESSAGE";

CREATE SEQUENCE "SEQ_DLOG_MUSIC";

CREATE SEQUENCE "SEQ_DLOG_MUSICBOX";

CREATE SEQUENCE "SEQ_DLOG_P_REPLY";

CREATE SEQUENCE "SEQ_DLOG_PHOTO";

CREATE SEQUENCE "SEQ_DLOG_SITE";

CREATE SEQUENCE "SEQ_DLOG_SITE_STAT";

CREATE SEQUENCE "SEQ_DLOG_T_REPLY";

CREATE SEQUENCE "SEQ_DLOG_TAG";

CREATE SEQUENCE "SEQ_DLOG_TOPIC";

CREATE SEQUENCE "SEQ_DLOG_TRACKBACK";

CREATE SEQUENCE "SEQ_DLOG_TYPE";

CREATE SEQUENCE "SEQ_DLOG_USER";

/*==============================================================*/
/* Table: "DLOG_ALBUM"                                          */
/*==============================================================*/
CREATE TABLE "DLOG_ALBUM"  (
   "ALBUM_ID"           NUMBER(6)                       NOT NULL,
   "PHOTO_ID"           NUMBER(6),
   "DLO_ALBUM_ID"       NUMBER(6),
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "DLOG_TYPE_ID"       NUMBER(6),
   "ALBUM_NAME"         VARCHAR2(40)                    NOT NULL,
   "ALBUM_DESC"         VARCHAR2(200),
   "PHOTO_COUNT"        INTEGER                         NOT NULL,
   "ALBUM_TYPE"         INTEGER                         NOT NULL,
   "VERIFYCODE"         VARCHAR2(20),
   "SORT_ORDER"         INTEGER                         NOT NULL,
   "CREATE_TIME"        DATE                            NOT NULL,
   CONSTRAINT PK_DLOG_ALBUM PRIMARY KEY ("ALBUM_ID")
);

/*==============================================================*/
/* Index: "R_SITE_ALBUM_FK"                                     */
/*==============================================================*/
CREATE INDEX "R_SITE_ALBUM_FK" ON "DLOG_ALBUM" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_ALBUM_FK"                                          */
/*==============================================================*/
CREATE INDEX "R_ALBUM_FK" ON "DLOG_ALBUM" (
   "DLO_ALBUM_ID" ASC
);

/*==============================================================*/
/* Index: "R_ALBUM_TYPE_FK"                                     */
/*==============================================================*/
CREATE INDEX "R_ALBUM_TYPE_FK" ON "DLOG_ALBUM" (
   "DLOG_TYPE_ID" ASC
);

/*==============================================================*/
/* Index: "R_ALBUM_COVER_FK"                                    */
/*==============================================================*/
CREATE INDEX "R_ALBUM_COVER_FK" ON "DLOG_ALBUM" (
   "PHOTO_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_BLOCKED_IP"                                     */
/*==============================================================*/
CREATE TABLE "DLOG_BLOCKED_IP"  (
   "BLOCKED_IP_ID"      NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6),
   "IP_ADDR"            INTEGER                         NOT NULL,
   "S_IP_ADDR"          VARCHAR2(16)                    NOT NULL,
   "IP_MASK"            INTEGER                         NOT NULL,
   "S_IP_MASK"          VARCHAR2(16)                    NOT NULL,
   "BLOCKED_TYPE"       SMALLINT                        NOT NULL,
   "BLOCKED_TIME"       DATE                            NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_BLOCKED_IP PRIMARY KEY ("BLOCKED_IP_ID")
);

/*==============================================================*/
/* Index: "R_SITE_BLOCKED_IP_FK"                                */
/*==============================================================*/
CREATE INDEX "R_SITE_BLOCKED_IP_FK" ON "DLOG_BLOCKED_IP" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_BOOKMARK"                                       */
/*==============================================================*/
CREATE TABLE "DLOG_BOOKMARK"  (
   "MARK_ID"            NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "PARENT_ID"          INTEGER                         NOT NULL,
   "PARENT_TYPE"        SMALLINT                        NOT NULL,
   "TITLE"              VARCHAR2(200),
   "URL"                VARCHAR2(200),
   "CREATE_TIME"        DATE                            NOT NULL,
   CONSTRAINT PK_DLOG_BOOKMARK PRIMARY KEY ("MARK_ID")
);

/*==============================================================*/
/* Index: "R_USER_MARK_FK"                                      */
/*==============================================================*/
CREATE INDEX "R_USER_MARK_FK" ON "DLOG_BOOKMARK" (
   "USERID" ASC
);

/*==============================================================*/
/* Index: "R_SITE_BOOKMARK_FK"                                  */
/*==============================================================*/
CREATE INDEX "R_SITE_BOOKMARK_FK" ON "DLOG_BOOKMARK" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_BULLETIN"                                       */
/*==============================================================*/
CREATE TABLE "DLOG_BULLETIN"  (
   "BULLETIN_ID"        NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "BULLETIN_TYPE"      INTEGER                         NOT NULL,
   "PUB_TIME"           DATE                            NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   "TITLE"              VARCHAR2(200)                   NOT NULL,
   "CONTENT"            CLOB                            NOT NULL,
   CONSTRAINT PK_DLOG_BULLETIN PRIMARY KEY ("BULLETIN_ID")
);

/*==============================================================*/
/* Index: "R_SITE_BULLETIN_FK"                                  */
/*==============================================================*/
CREATE INDEX "R_SITE_BULLETIN_FK" ON "DLOG_BULLETIN" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_CATALOG"                                        */
/*==============================================================*/
CREATE TABLE "DLOG_CATALOG"  (
   "CATALOG_ID"         NUMBER(6)                       NOT NULL,
   "DLOG_TYPE_ID"       NUMBER(6),
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "CATALOG_NAME"       VARCHAR2(20)                    NOT NULL,
   "CATALOG_DESC"       VARCHAR2(200),
   "CREATE_TIME"        DATE                            NOT NULL,
   "ARTICLE_COUNT"      INTEGER                         NOT NULL,
   "CATALOG_TYPE"       SMALLINT                        NOT NULL,
   "VERIFYCODE"         VARCHAR2(20),
   "SORT_ORDER"         INTEGER                         NOT NULL,
   CONSTRAINT PK_DLOG_CATALOG PRIMARY KEY ("CATALOG_ID")
);

/*==============================================================*/
/* Index: "R_SITE_CATALOG_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_SITE_CATALOG_FK" ON "DLOG_CATALOG" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_CATALOG_TYPE_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_CATALOG_TYPE_FK" ON "DLOG_CATALOG" (
   "DLOG_TYPE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_CATALOG_PERM"                                   */
/*==============================================================*/
CREATE TABLE "DLOG_CATALOG_PERM"  (
   "CATALOG_ID"         NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6)                       NOT NULL,
   "USER_ROLE"          INTEGER                         NOT NULL,
   CONSTRAINT PK_DLOG_CATALOG_PERM PRIMARY KEY ("CATALOG_ID", "USERID")
);

/*==============================================================*/
/* Index: "DLOG_CATALOG_PERM_FK"                                */
/*==============================================================*/
CREATE INDEX "DLOG_CATALOG_PERM_FK" ON "DLOG_CATALOG_PERM" (
   "CATALOG_ID" ASC
);

/*==============================================================*/
/* Index: "DLOG_CATALOG_PERM2_FK"                               */
/*==============================================================*/
CREATE INDEX "DLOG_CATALOG_PERM2_FK" ON "DLOG_CATALOG_PERM" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_COMMENTS"                                       */
/*==============================================================*/
CREATE TABLE "DLOG_COMMENTS"  (
   "COMMENT_ID"         NUMBER(6)                       NOT NULL,
   "DLO_COMMENT_ID"     NUMBER(6),
   "SITE_ID"            NUMBER(6),
   "ENTITY_ID"          INTEGER                         NOT NULL,
   "ENTITY_TYPE"        INTEGER                         NOT NULL,
   "CLIENT_IP"          VARCHAR2(16)                    NOT NULL,
   "CLIENT_TYPE"        SMALLINT                        NOT NULL,
   "CLIENT_USER_AGENT"  VARCHAR2(100),
   "AUTHOR_ID"          INTEGER                         NOT NULL,
   "AUTHOR"             VARCHAR2(20)                    NOT NULL,
   "AUTHOR_EMAIL"       VARCHAR2(50),
   "AUTHOR_URL"         VARCHAR2(100),
   "TITLE"              VARCHAR2(200)                   NOT NULL,
   "CONTENT"            CLOB                            NOT NULL,
   "CONTENT_FORMAT"     SMALLINT                        NOT NULL,
   "COMMENT_TIME"       DATE                            NOT NULL,
   "COMMENT_FLAG"       SMALLINT                        NOT NULL,
   "COMMENT_STATUS"     SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_COMMENTS PRIMARY KEY ("COMMENT_ID")
);

/*==============================================================*/
/* Index: "R_SUB_COMMENT_FK"                                    */
/*==============================================================*/
CREATE INDEX "R_SUB_COMMENT_FK" ON "DLOG_COMMENTS" (
   "DLO_COMMENT_ID" ASC
);

/*==============================================================*/
/* Index: "R_SITE_COMMENT_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_SITE_COMMENT_FK" ON "DLOG_COMMENTS" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_CONFIG"                                         */
/*==============================================================*/
CREATE TABLE "DLOG_CONFIG"  (
   "CONFIG_ID"          NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6),
   "CONFIG_NAME"        VARCHAR2(20)                    NOT NULL,
   "INT_VALUE"          INTEGER,
   "STRING_VALUE"       VARCHAR2(100),
   "DATE_VALUE"         DATE,
   "TIME_VALUE"         DATE,
   "TIMESTAMP_VALUE"    DATE,
   "LAST_UPDATE"        TIMESTAMP                       NOT NULL,
   CONSTRAINT PK_DLOG_CONFIG PRIMARY KEY ("CONFIG_ID")
);

/*==============================================================*/
/* Index: "R_SITE_CONFIG_FK"                                    */
/*==============================================================*/
CREATE INDEX "R_SITE_CONFIG_FK" ON "DLOG_CONFIG" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_DIARY"                                          */
/*==============================================================*/
CREATE TABLE "DLOG_DIARY"  (
   "DIARY_ID"           NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "CATALOG_ID"         NUMBER(6)                       NOT NULL,
   "AUTHOR"             VARCHAR2(20)                    NOT NULL,
   "AUTHOR_URL"         VARCHAR2(100),
   "TITLE"              VARCHAR2(200)                   NOT NULL,
   "CONTENT"            CLOB                            NOT NULL,
   "DIARY_SIZE"         INTEGER                         NOT NULL,
   "REFER"              VARCHAR2(100),
   "WEATHER"            VARCHAR2(20)                    NOT NULL,
   "MOOD_LEVEL"         SMALLINT                        NOT NULL,
   "TAGS"               VARCHAR2(100),
   "BGSOUND"            INTEGER,
   "REPLY_COUNT"        INTEGER                         NOT NULL,
   "VIEW_COUNT"         INTEGER                         NOT NULL,
   "TB_COUNT"           INTEGER                         NOT NULL,
   "CLIENT_TYPE"        SMALLINT                        NOT NULL,
   "CLIENT_IP"          VARCHAR2(16)                    NOT NULL,
   "CLIENT_USER_AGENT"  VARCHAR2(100),
   "WRITE_TIME"         DATE                            NOT NULL,
   "LAST_READ_TIME"     DATE,
   "LAST_REPLY_TIME"    DATE,
   "MODIFY_TIME"        DATE,
   "REPLY_NOTIFY"       SMALLINT                        NOT NULL,
   "DIARY_TYPE"         SMALLINT                        NOT NULL,
   "LOCKED"             SMALLINT                        NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_DIARY PRIMARY KEY ("DIARY_ID")
);

/*==============================================================*/
/* Index: "R_SITE_JOURNAL_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_SITE_JOURNAL_FK" ON "DLOG_DIARY" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_CATALOG_DIARY_FK"                                  */
/*==============================================================*/
CREATE INDEX "R_CATALOG_DIARY_FK" ON "DLOG_DIARY" (
   "CATALOG_ID" ASC
);

/*==============================================================*/
/* Index: "R_USER_JOURNAL_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_USER_JOURNAL_FK" ON "DLOG_DIARY" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_EXTERNAL_REFER"                                 */
/*==============================================================*/
CREATE TABLE "DLOG_EXTERNAL_REFER"  (
   "REFER_ID"           NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6),
   "REF_ID"             INTEGER                         NOT NULL,
   "REF_TYPE"           SMALLINT                        NOT NULL,
   "REFER_HOST"         VARCHAR2(50),
   "REFER_URL"          VARCHAR2(250)                   NOT NULL,
   "CLIENT_IP"          VARCHAR2(16)                    NOT NULL,
   "REFER_TIME"         DATE                            NOT NULL,
   CONSTRAINT PK_DLOG_EXTERNAL_REFER PRIMARY KEY ("REFER_ID")
);

/*==============================================================*/
/* Index: "R_SITE_REFER_FK"                                     */
/*==============================================================*/
CREATE INDEX "R_SITE_REFER_FK" ON "DLOG_EXTERNAL_REFER" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_FCK_UPLOAD_FILE"                                */
/*==============================================================*/
CREATE TABLE "DLOG_FCK_UPLOAD_FILE"  (
   "FCK_FILE_ID"        NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6),
   "UPLOAD_TIME"        DATE                            NOT NULL,
   "SESSION_ID"         VARCHAR2(100)                   NOT NULL,
   "REF_ID"             INTEGER                         NOT NULL,
   "REF_TYPE"           SMALLINT                        NOT NULL,
   "SAVE_PATH"          VARCHAR2(255)                   NOT NULL,
   "FILE_URI"           VARCHAR2(100)                   NOT NULL,
   "FILE_TYPE"          INTEGER                         NOT NULL,
   "FILE_SIZE"          INTEGER                         NOT NULL,
   CONSTRAINT PK_DLOG_FCK_UPLOAD_FILE PRIMARY KEY ("FCK_FILE_ID")
);

/*==============================================================*/
/* Index: "R_SITE_FILE_FK"                                      */
/*==============================================================*/
CREATE INDEX "R_SITE_FILE_FK" ON "DLOG_FCK_UPLOAD_FILE" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_USER_UPLOAD_FK"                                    */
/*==============================================================*/
CREATE INDEX "R_USER_UPLOAD_FK" ON "DLOG_FCK_UPLOAD_FILE" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_FORUM"                                          */
/*==============================================================*/
CREATE TABLE "DLOG_FORUM"  (
   "FORUM_ID"           NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "DLOG_TYPE_ID"       NUMBER(6),
   "FORUM_NAME"         VARCHAR2(40)                    NOT NULL,
   "FORUM_DESC"         VARCHAR2(200),
   "FORUM_TYPE"         INTEGER                         NOT NULL,
   "CREATE_TIME"        DATE                            NOT NULL,
   "MODIFY_TIME"        DATE,
   "LAST_TIME"          DATE,
   "LAST_USER_ID"       INTEGER,
   "LAST_USER_NAME"     VARCHAR2(50),
   "LAST_TOPIC_ID"      INTEGER,
   "SORT_ORDER"         INTEGER                         NOT NULL,
   "TOPIC_COUNT"        INTEGER                         NOT NULL,
   "FORUM_OPTION"       INTEGER                         NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_FORUM PRIMARY KEY ("FORUM_ID")
);

/*==============================================================*/
/* Index: "R_SITE_FORUM_FK"                                     */
/*==============================================================*/
CREATE INDEX "R_SITE_FORUM_FK" ON "DLOG_FORUM" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_FORUM_TYPE_FK"                                     */
/*==============================================================*/
CREATE INDEX "R_FORUM_TYPE_FK" ON "DLOG_FORUM" (
   "DLOG_TYPE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_FRIEND"                                         */
/*==============================================================*/
CREATE TABLE "DLOG_FRIEND"  (
   "USER_ID"            INTEGER                         NOT NULL,
   "FRIEND_ID"          INTEGER                         NOT NULL,
   "FRIEND_TYPE"        INTEGER                         NOT NULL,
   "FRIEND_ROLE"        INTEGER                         NOT NULL,
   "ADD_TIME"           DATE                            NOT NULL,
   CONSTRAINT PK_DLOG_FRIEND PRIMARY KEY ("USER_ID", "FRIEND_ID")
);

/*==============================================================*/
/* Table: "DLOG_GUESTBOOK"                                      */
/*==============================================================*/
CREATE TABLE "DLOG_GUESTBOOK"  (
   "GUEST_BOOK_ID"      NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "CONTENT"            CLOB                            NOT NULL,
   "REPLY_CONTENT"      CLOB,
   "CLIENT_TYPE"        SMALLINT                        NOT NULL,
   "CLIENT_IP"          VARCHAR2(16)                    NOT NULL,
   "CLIENT_USER_AGENT"  VARCHAR2(100),
   "CREATE_TIME"        DATE                            NOT NULL,
   "REPLY_TIME"         DATE,
   CONSTRAINT PK_DLOG_GUESTBOOK PRIMARY KEY ("GUEST_BOOK_ID")
);

/*==============================================================*/
/* Index: "R_SITE_GUEST_FK"                                     */
/*==============================================================*/
CREATE INDEX "R_SITE_GUEST_FK" ON "DLOG_GUESTBOOK" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_USER_LIUYAN_FK"                                    */
/*==============================================================*/
CREATE INDEX "R_USER_LIUYAN_FK" ON "DLOG_GUESTBOOK" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_J_REPLY"                                        */
/*==============================================================*/
CREATE TABLE "DLOG_J_REPLY"  (
   "J_REPLY_ID"         NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6),
   "DIARY_ID"           NUMBER(6)                       NOT NULL,
   "AUTHOR"             VARCHAR2(20)                    NOT NULL,
   "AUTHOR_URL"         VARCHAR2(100),
   "AUTHOR_EMAIL"       VARCHAR2(50),
   "CLIENT_TYPE"        SMALLINT                        NOT NULL,
   "CLIENT_IP"          VARCHAR2(16)                    NOT NULL,
   "CLIENT_USER_AGENT"  VARCHAR2(100),
   "OWNER_ONLY"         INTEGER                         NOT NULL,
   "CONTENT"            CLOB                            NOT NULL,
   "WRITE_TIME"         DATE                            NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_J_REPLY PRIMARY KEY ("J_REPLY_ID")
);

/*==============================================================*/
/* Index: "R_SITE_J_REPLY_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_SITE_J_REPLY_FK" ON "DLOG_J_REPLY" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_JOURNAL_REPLY_FK"                                  */
/*==============================================================*/
CREATE INDEX "R_JOURNAL_REPLY_FK" ON "DLOG_J_REPLY" (
   "DIARY_ID" ASC
);

/*==============================================================*/
/* Index: "R_USER_J_REPLY_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_USER_J_REPLY_FK" ON "DLOG_J_REPLY" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_LINK"                                           */
/*==============================================================*/
CREATE TABLE "DLOG_LINK"  (
   "LINKID"             NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "LINK_TITLE"         VARCHAR2(40)                    NOT NULL,
   "LINK_URL"           VARCHAR2(200)                   NOT NULL,
   "LINK_TYPE"          SMALLINT                        NOT NULL,
   "SORT_ORDER"         INTEGER                         NOT NULL,
   "CREATE_TIME"        DATE                            NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_LINK PRIMARY KEY ("LINKID")
);

/*==============================================================*/
/* Index: "R_SITE_LINK_FK"                                      */
/*==============================================================*/
CREATE INDEX "R_SITE_LINK_FK" ON "DLOG_LINK" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_MESSAGE"                                        */
/*==============================================================*/
CREATE TABLE "DLOG_MESSAGE"  (
   "MSGID"              NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6),
   "FROM_USER_ID"       INTEGER                         NOT NULL,
   "CONTENT"            CLOB                            NOT NULL,
   "SEND_TIME"          DATE                            NOT NULL,
   "EXPIRE_TIME"        DATE,
   "READ_TIME"          DATE,
   "STATUS"             SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_MESSAGE PRIMARY KEY ("MSGID")
);

/*==============================================================*/
/* Index: "R_MSG_RECEIVER_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_MSG_RECEIVER_FK" ON "DLOG_MESSAGE" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_MUSIC"                                          */
/*==============================================================*/
CREATE TABLE "DLOG_MUSIC"  (
   "MUSIC_ID"           NUMBER(6)                       NOT NULL,
   "MUSIC_BOX_ID"       NUMBER(6),
   "USERID"             NUMBER(6),
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "MUSIC_TITLE"        VARCHAR2(100)                   NOT NULL,
   "MUSIC_WORD"         CLOB,
   "ALBUM"              VARCHAR2(100),
   "SINGER"             VARCHAR2(50),
   "URL"                VARCHAR2(200),
   "CREATE_TIME"        DATE                            NOT NULL,
   "VIEW_COUNT"         INTEGER                         NOT NULL,
   "MUSIC_TYPE"         INTEGER                         NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_MUSIC PRIMARY KEY ("MUSIC_ID")
);

/*==============================================================*/
/* Index: "R_SITE_MUSIC_FK"                                     */
/*==============================================================*/
CREATE INDEX "R_SITE_MUSIC_FK" ON "DLOG_MUSIC" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_MUSIC_BOX_FK"                                      */
/*==============================================================*/
CREATE INDEX "R_MUSIC_BOX_FK" ON "DLOG_MUSIC" (
   "MUSIC_BOX_ID" ASC
);

/*==============================================================*/
/* Index: "R_RECOMMEND_FK"                                      */
/*==============================================================*/
CREATE INDEX "R_RECOMMEND_FK" ON "DLOG_MUSIC" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_MUSICBOX"                                       */
/*==============================================================*/
CREATE TABLE "DLOG_MUSICBOX"  (
   "MUSIC_BOX_ID"       NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "BOX_NAME"           VARCHAR2(40)                    NOT NULL,
   "BOX_DESC"           VARCHAR2(100),
   "MUSIC_COUNT"        INTEGER                         NOT NULL,
   "CREATE_TIME"        DATE                            NOT NULL,
   "SORT_ORDER"         INTEGER                         NOT NULL,
   CONSTRAINT PK_DLOG_MUSICBOX PRIMARY KEY ("MUSIC_BOX_ID")
);

/*==============================================================*/
/* Index: "R_SITE_MBOX_FK"                                      */
/*==============================================================*/
CREATE INDEX "R_SITE_MBOX_FK" ON "DLOG_MUSICBOX" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_MY_BLACKLIST"                                   */
/*==============================================================*/
CREATE TABLE "DLOG_MY_BLACKLIST"  (
   "MY_USER_ID"         INTEGER                         NOT NULL,
   "OTHER_USER_ID"      INTEGER                         NOT NULL,
   "BL_TYPE"            INTEGER                         NOT NULL,
   "ADD_TIME"           DATE                            NOT NULL,
   CONSTRAINT PK_DLOG_MY_BLACKLIST PRIMARY KEY ("MY_USER_ID", "OTHER_USER_ID")
);

/*==============================================================*/
/* Table: "DLOG_P_REPLY"                                        */
/*==============================================================*/
CREATE TABLE "DLOG_P_REPLY"  (
   "P_REPLY_ID"         NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "PHOTO_ID"           NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6),
   "AUTHOR"             VARCHAR2(20)                    NOT NULL,
   "AUTHOR_URL"         VARCHAR2(100),
   "AUTHOR_EMAIL"       VARCHAR2(50),
   "CLIENT_TYPE"        SMALLINT                        NOT NULL,
   "CLIENT_IP"          VARCHAR2(16)                    NOT NULL,
   "CLIENT_USER_AGENT"  VARCHAR2(100),
   "OWNER_ONLY"         INTEGER                         NOT NULL,
   "CONTENT"            CLOB                            NOT NULL,
   "WRITE_TIME"         DATE                            NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_P_REPLY PRIMARY KEY ("P_REPLY_ID")
);

/*==============================================================*/
/* Index: "R_PHOTO_REPLY_FK"                                    */
/*==============================================================*/
CREATE INDEX "R_PHOTO_REPLY_FK" ON "DLOG_P_REPLY" (
   "PHOTO_ID" ASC
);

/*==============================================================*/
/* Index: "R_SITE_P_REPLY_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_SITE_P_REPLY_FK" ON "DLOG_P_REPLY" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_USER_P_REPLY_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_USER_P_REPLY_FK" ON "DLOG_P_REPLY" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_PHOTO"                                          */
/*==============================================================*/
CREATE TABLE "DLOG_PHOTO"  (
   "PHOTO_ID"           NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "ALBUM_ID"           NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6)                       NOT NULL,
   "PHOTO_NAME"         VARCHAR2(40)                    NOT NULL,
   "PHOTO_DESC"         CLOB,
   "FILE_NAME"          VARCHAR2(100)                   NOT NULL,
   "PHOTO_URL"          VARCHAR2(100)                   NOT NULL,
   "PREVIEW_URL"        VARCHAR2(100)                   NOT NULL,
   "TAGS"               VARCHAR2(100),
   "P_YEAR"             INTEGER                         NOT NULL,
   "P_MONTH"            SMALLINT                        NOT NULL,
   "P_DATE"             SMALLINT                        NOT NULL,
   "WIDTH"              INTEGER                         NOT NULL,
   "HEIGHT"             INTEGER                         NOT NULL,
   "PHOTO_SIZE"         INTEGER                         NOT NULL,
   "COLOR_BIT"          INTEGER                         NOT NULL,
   "EXIF_MANUFACTURER"  VARCHAR2(50),
   "EXIF_MODEL"         VARCHAR2(50),
   "EXIF_ISO"           INTEGER                         NOT NULL,
   "EXIF_APERTURE"      VARCHAR2(20),
   "EXIF_SHUTTER"       VARCHAR2(20),
   "EXIF_EXPOSURE_BIAS" VARCHAR2(20),
   "EXIF_EXPOSURE_TIME" VARCHAR2(20),
   "EXIF_FOCAL_LENGTH"  VARCHAR2(20),
   "EXIF_COLOR_SPACE"   VARCHAR2(20),
   "REPLY_COUNT"        INTEGER                         NOT NULL,
   "VIEW_COUNT"         INTEGER                         NOT NULL,
   "CREATE_TIME"        DATE                            NOT NULL,
   "MODIFY_TIME"        DATE,
   "LAST_REPLY_TIME"    DATE,
   "PHOTO_TYPE"         INTEGER                         NOT NULL,
   "LOCKED"             SMALLINT                        NOT NULL,
   "PHOTO_STATUS"       SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_PHOTO PRIMARY KEY ("PHOTO_ID")
);

/*==============================================================*/
/* Index: "R_ALBUM_PHOTO_FK"                                    */
/*==============================================================*/
CREATE INDEX "R_ALBUM_PHOTO_FK" ON "DLOG_PHOTO" (
   "ALBUM_ID" ASC
);

/*==============================================================*/
/* Index: "R_SITE_PHOTO_FK"                                     */
/*==============================================================*/
CREATE INDEX "R_SITE_PHOTO_FK" ON "DLOG_PHOTO" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_PHOTO_OWNER_FK"                                    */
/*==============================================================*/
CREATE INDEX "R_PHOTO_OWNER_FK" ON "DLOG_PHOTO" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_SITE"                                           */
/*==============================================================*/
CREATE TABLE "DLOG_SITE"  (
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6)                       NOT NULL,
   "DLOG_TYPE_ID"       NUMBER(6),
   "SITE_NAME"          VARCHAR2(20)                    NOT NULL,
   "SITE_C_NAME"        VARCHAR2(50)                    NOT NULL,
   "SITE_URL"           VARCHAR2(100),
   "SITE_TITLE"         VARCHAR2(100),
   "SITE_DETAIL"        VARCHAR2(250),
   "SITE_ICP"           VARCHAR2(20),
   "SITE_LOGO"          VARCHAR2(50),
   "SITE_CSS"           VARCHAR2(50),
   "SITE_LAYOUT"        VARCHAR2(50),
   "SITE_LANG"          VARCHAR2(10),
   "SITE_FLAG"          INTEGER                         NOT NULL,
   "CREATE_TIME"        DATE                            NOT NULL,
   "LAST_TIME"          DATE,
   "EXPIRED_TIME"       DATE,
   "LAST_EXP_TIME"      DATE,
   "ACCESS_MODE"        SMALLINT                        NOT NULL,
   "ACCESS_CODE"        VARCHAR2(50),
   "DIARY_STATUS"       SMALLINT                        NOT NULL,
   "PHOTO_STATUS"       SMALLINT                        NOT NULL,
   "MUSIC_STATUS"       SMALLINT                        NOT NULL,
   "FORUM_STATUS"       SMALLINT                        NOT NULL,
   "GUESTBOOK_STATUS"   SMALLINT                        NOT NULL,
   "DIARY_CNAME"        VARCHAR2(16),
   "PHOTO_CNAME"        VARCHAR2(16),
   "MUSIC_CNAME"        VARCHAR2(16),
   "BBS_CNAME"          VARCHAR2(16),
   "GUESTBOOK_CNAME"    VARCHAR2(16),
   "PHOTO_SPACE_TOTAL"  INTEGER                         NOT NULL,
   "PHOTO_SPACE_USED"   INTEGER                         NOT NULL,
   "DIARY_SPACE_TOTAL"  INTEGER                         NOT NULL,
   "DIARY_SPACE_USED"   INTEGER                         NOT NULL,
   "MEDIA_SPACE_TOTAL"  INTEGER                         NOT NULL,
   "MEDIA_SPACE_USED"   INTEGER                         NOT NULL,
   "SITE_TYPE"          INTEGER                         NOT NULL,
   "SITE_LEVEL"         INTEGER                         NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   CONSTRAINT PK_DLOG_SITE PRIMARY KEY ("SITE_ID")
);

/*==============================================================*/
/* Index: "R_USER_SITE_FK"                                      */
/*==============================================================*/
CREATE INDEX "R_USER_SITE_FK" ON "DLOG_SITE" (
   "USERID" ASC
);

/*==============================================================*/
/* Index: "R_SITE_TYPE_FK"                                      */
/*==============================================================*/
CREATE INDEX "R_SITE_TYPE_FK" ON "DLOG_SITE" (
   "DLOG_TYPE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_SITE_STAT"                                      */
/*==============================================================*/
CREATE TABLE "DLOG_SITE_STAT"  (
   "SITE_STAT_ID"       NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6),
   "STAT_DATE"          INTEGER                         NOT NULL,
   "UV_COUNT"           INTEGER                         NOT NULL,
   "PV_COUNT"           INTEGER                         NOT NULL,
   "V_SOURCE"           INTEGER                         NOT NULL,
   "UPDATE_TIME"        DATE                            NOT NULL,
   CONSTRAINT PK_DLOG_SITE_STAT PRIMARY KEY ("SITE_STAT_ID")
);

/*==============================================================*/
/* Index: "R_SITE_STAT_FK"                                      */
/*==============================================================*/
CREATE INDEX "R_SITE_STAT_FK" ON "DLOG_SITE_STAT" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_T_REPLY"                                        */
/*==============================================================*/
CREATE TABLE "DLOG_T_REPLY"  (
   "T_REPLY_ID"         NUMBER(6)                       NOT NULL,
   "TOPIC_ID"           NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "TITLE"              VARCHAR2(200)                   NOT NULL,
   "CONTENT"            CLOB                            NOT NULL,
   "WRITE_TIME"         DATE,
   "STATUS"             SMALLINT,
   "CLIENT_IP"          VARCHAR2(16),
   "CLIENT_TYPE"        SMALLINT,
   "CLIENT_USER_AGENT"  VARCHAR2(100),
   CONSTRAINT PK_DLOG_T_REPLY PRIMARY KEY ("T_REPLY_ID")
);

/*==============================================================*/
/* Index: "R_SITE_T_REPLY_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_SITE_T_REPLY_FK" ON "DLOG_T_REPLY" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_TOPIC_REPLY_FK"                                    */
/*==============================================================*/
CREATE INDEX "R_TOPIC_REPLY_FK" ON "DLOG_T_REPLY" (
   "TOPIC_ID" ASC
);

/*==============================================================*/
/* Index: "R_USER_T_REPLY_FK"                                   */
/*==============================================================*/
CREATE INDEX "R_USER_T_REPLY_FK" ON "DLOG_T_REPLY" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_TAG"                                            */
/*==============================================================*/
CREATE TABLE "DLOG_TAG"  (
   "TAG_ID"             NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "REF_ID"             INTEGER                         NOT NULL,
   "REF_TYPE"           SMALLINT                        NOT NULL,
   "TAG_NAME"           VARCHAR2(20)                    NOT NULL,
   CONSTRAINT PK_DLOG_TAG PRIMARY KEY ("TAG_ID")
);

/*==============================================================*/
/* Index: "R_SITE_TAG_FK"                                       */
/*==============================================================*/
CREATE INDEX "R_SITE_TAG_FK" ON "DLOG_TAG" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_TOPIC"                                          */
/*==============================================================*/
CREATE TABLE "DLOG_TOPIC"  (
   "TOPIC_ID"           NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6)                       NOT NULL,
   "USERID"             NUMBER(6)                       NOT NULL,
   "FORUM_ID"           NUMBER(6)                       NOT NULL,
   "USERNAME"           VARCHAR2(20)                    NOT NULL,
   "CREATE_TIME"        DATE                            NOT NULL,
   "MODIFY_TIME"        DATE,
   "TITLE"              VARCHAR2(200)                   NOT NULL,
   "CONTENT"            CLOB                            NOT NULL,
   "TAGS"               VARCHAR2(100),
   "LAST_REPLY_TIME"    DATE,
   "LAST_REPLY_ID"      INTEGER,
   "LAST_USER_ID"       INTEGER,
   "LAST_USER_NAME"     VARCHAR2(50),
   "REPLY_COUNT"        INTEGER                         NOT NULL,
   "VIEW_COUNT"         INTEGER                         NOT NULL,
   "LOCKED"             SMALLINT                        NOT NULL,
   "TOPIC_TYPE"         INTEGER                         NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   "CLIENT_TYPE"        SMALLINT                        NOT NULL,
   "CLIENT_IP"          VARCHAR2(16)                    NOT NULL,
   "CLIENT_USER_AGENT"  VARCHAR2(100),
   CONSTRAINT PK_DLOG_TOPIC PRIMARY KEY ("TOPIC_ID")
);

/*==============================================================*/
/* Index: "R_SITE_TOPIC_FK"                                     */
/*==============================================================*/
CREATE INDEX "R_SITE_TOPIC_FK" ON "DLOG_TOPIC" (
   "SITE_ID" ASC
);

/*==============================================================*/
/* Index: "R_FORUM_TOPIC_FK"                                    */
/*==============================================================*/
CREATE INDEX "R_FORUM_TOPIC_FK" ON "DLOG_TOPIC" (
   "FORUM_ID" ASC
);

/*==============================================================*/
/* Index: "R_USER_TOPIC_FK"                                     */
/*==============================================================*/
CREATE INDEX "R_USER_TOPIC_FK" ON "DLOG_TOPIC" (
   "USERID" ASC
);

/*==============================================================*/
/* Table: "DLOG_TRACKBACK"                                      */
/*==============================================================*/
CREATE TABLE "DLOG_TRACKBACK"  (
   "TRACK_ID"           NUMBER(6)                       NOT NULL,
   "PARENT_ID"          INTEGER                         NOT NULL,
   "PARENT_TYPE"        SMALLINT                        NOT NULL,
   "REFURL"             VARCHAR2(100)                   NOT NULL,
   "TITLE"              VARCHAR2(200),
   "EXCERPT"            VARCHAR2(200),
   "BLOG_NAME"          VARCHAR2(50)                    NOT NULL,
   "REMOTE_ADDR"        CHAR(15)                        NOT NULL,
   "TRACK_TIME"         DATE                            NOT NULL,
   CONSTRAINT PK_DLOG_TRACKBACK PRIMARY KEY ("TRACK_ID")
);

/*==============================================================*/
/* Table: "DLOG_TYPE"                                           */
/*==============================================================*/
CREATE TABLE "DLOG_TYPE"  (
   "DLOG_TYPE_ID"       NUMBER(6)                       NOT NULL,
   "DLO_DLOG_TYPE_ID"   NUMBER(6),
   "TYPE_NAME"          VARCHAR2(20)                    NOT NULL,
   "SORT_ORDER"         INTEGER                         NOT NULL,
   CONSTRAINT PK_DLOG_TYPE PRIMARY KEY ("DLOG_TYPE_ID")
);

/*==============================================================*/
/* Index: "R_SITE_TYPE_TREE_FK"                                 */
/*==============================================================*/
CREATE INDEX "R_SITE_TYPE_TREE_FK" ON "DLOG_TYPE" (
   "DLO_DLOG_TYPE_ID" ASC
);

/*==============================================================*/
/* Table: "DLOG_USER"                                           */
/*==============================================================*/
CREATE TABLE "DLOG_USER"  (
   "USERID"             NUMBER(6)                       NOT NULL,
   "SITE_ID"            NUMBER(6),
   "OWN_SITE_ID"        INTEGER                         NOT NULL,
   "USERNAME"           VARCHAR2(20)                    NOT NULL,
   "PASSWORD"           VARCHAR2(50)                    NOT NULL,
   "USER_ROLE"          INTEGER                         NOT NULL,
   "NICKNAME"           VARCHAR2(50)                    NOT NULL,
   "SEX"                SMALLINT                        NOT NULL,
   "BIRTH"              DATE,
   "EMAIL"              VARCHAR2(50),
   "HOMEPAGE"           VARCHAR2(50),
   "QQ"                 VARCHAR2(16),
   "MSN"                VARCHAR2(50),
   "MOBILE"             VARCHAR2(16),
   "NATION"             VARCHAR2(40),
   "PROVINCE"           VARCHAR2(40),
   "CITY"               VARCHAR2(40),
   "INDUSTRY"           VARCHAR2(40),
   "COMPANY"            VARCHAR2(80),
   "ADDRESS"            VARCHAR2(200),
   "JOB"                VARCHAR2(40),
   "FAX"                VARCHAR2(20),
   "TEL"                VARCHAR2(20),
   "ZIP"                VARCHAR2(12),
   "PORTRAIT"           VARCHAR2(100),
   "RESUME"             VARCHAR2(200),
   "REGTIME"            DATE                            NOT NULL,
   "LAST_TIME"          DATE,
   "LAST_IP"            VARCHAR2(16),
   "KEEP_DAYS"          INTEGER                         NOT NULL,
   "ONLINE_STATUS"      INTEGER                         NOT NULL,
   "USER_LEVEL"         INTEGER                         NOT NULL,
   "STATUS"             SMALLINT                        NOT NULL,
   "ARTICLE_COUNT"      INTEGER                         NOT NULL,
   "ARTICLE_REPLY_COUNT" INTEGER                         NOT NULL,
   "TOPIC_COUNT"        INTEGER                         NOT NULL,
   "TOPIC_REPLY_COUNT"  INTEGER                         NOT NULL,
   "PHOTO_COUNT"        INTEGER                         NOT NULL,
   "PHOTO_REPLY_COUNT"  INTEGER                         NOT NULL,
   "GUESTBOOK_COUNT"    INTEGER                         NOT NULL,
   "BOOKMARK_COUNT"     INTEGER                         NOT NULL,
   CONSTRAINT PK_DLOG_USER PRIMARY KEY ("USERID")
);

/*==============================================================*/
/* Index: "R_SITE_USER_FK"                                      */
/*==============================================================*/
CREATE INDEX "R_SITE_USER_FK" ON "DLOG_USER" (
   "SITE_ID" ASC
);

ALTER TABLE "DLOG_ALBUM"
   ADD CONSTRAINT FK_DLOG_ALB_R_ALBUM_DLOG_ALB FOREIGN KEY ("DLO_ALBUM_ID")
      REFERENCES "DLOG_ALBUM" ("ALBUM_ID");

ALTER TABLE "DLOG_ALBUM"
   ADD CONSTRAINT FK_DLOG_ALB_R_ALBUM_C_DLOG_PHO FOREIGN KEY ("PHOTO_ID")
      REFERENCES "DLOG_PHOTO" ("PHOTO_ID");

ALTER TABLE "DLOG_ALBUM"
   ADD CONSTRAINT FK_DLOG_ALB_R_ALBUM_T_DLOG_TYP FOREIGN KEY ("DLOG_TYPE_ID")
      REFERENCES "DLOG_TYPE" ("DLOG_TYPE_ID");

ALTER TABLE "DLOG_ALBUM"
   ADD CONSTRAINT FK_DLOG_ALB_R_SITE_AL_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_BLOCKED_IP"
   ADD CONSTRAINT FK_DLOG_BLO_R_SITE_BL_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_BOOKMARK"
   ADD CONSTRAINT FK_DLOG_BOO_R_SITE_BO_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_BOOKMARK"
   ADD CONSTRAINT FK_DLOG_BOO_R_USER_MA_DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_BULLETIN"
   ADD CONSTRAINT FK_DLOG_BUL_R_SITE_BU_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_CATALOG"
   ADD CONSTRAINT FK_DLOG_CAT_R_CATALOG_DLOG_TYP FOREIGN KEY ("DLOG_TYPE_ID")
      REFERENCES "DLOG_TYPE" ("DLOG_TYPE_ID");

ALTER TABLE "DLOG_CATALOG"
   ADD CONSTRAINT FK_DLOG_CAT_R_SITE_CA_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_CATALOG_PERM"
   ADD CONSTRAINT FK_DLOG_CAT_DLOG_CATA_DLOG_CAT FOREIGN KEY ("CATALOG_ID")
      REFERENCES "DLOG_CATALOG" ("CATALOG_ID");

ALTER TABLE "DLOG_CATALOG_PERM"
   ADD CONSTRAINT FK_DLOG_CAT_DLOG_CATA_DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_COMMENTS"
   ADD CONSTRAINT FK_DLOG_COM_R_SITE_CO_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_COMMENTS"
   ADD CONSTRAINT FK_DLOG_COM_R_SUB_COM_DLOG_COM FOREIGN KEY ("DLO_COMMENT_ID")
      REFERENCES "DLOG_COMMENTS" ("COMMENT_ID");

ALTER TABLE "DLOG_CONFIG"
   ADD CONSTRAINT FK_DLOG_CON_R_SITE_CO_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_DIARY"
   ADD CONSTRAINT FK_DLOG_DIA_R_CATALOG_DLOG_CAT FOREIGN KEY ("CATALOG_ID")
      REFERENCES "DLOG_CATALOG" ("CATALOG_ID");

ALTER TABLE "DLOG_DIARY"
   ADD CONSTRAINT FK_DLOG_DIA_R_SITE_JO_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_DIARY"
   ADD CONSTRAINT FK_DLOG_DIA_R_USER_JO_DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_EXTERNAL_REFER"
   ADD CONSTRAINT FK_DLOG_EXT_R_SITE_RE_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_FCK_UPLOAD_FILE"
   ADD CONSTRAINT FK_DLOG_FCK_R_SITE_FI_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_FCK_UPLOAD_FILE"
   ADD CONSTRAINT FK_DLOG_FCK_R_USER_UP_DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_FORUM"
   ADD CONSTRAINT FK_DLOG_FOR_R_FORUM_T_DLOG_TYP FOREIGN KEY ("DLOG_TYPE_ID")
      REFERENCES "DLOG_TYPE" ("DLOG_TYPE_ID");

ALTER TABLE "DLOG_FORUM"
   ADD CONSTRAINT FK_DLOG_FOR_R_SITE_FO_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_GUESTBOOK"
   ADD CONSTRAINT FK_DLOG_GUE_R_SITE_GU_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_GUESTBOOK"
   ADD CONSTRAINT FK_DLOG_GUE_R_USER_LI_DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_J_REPLY"
   ADD CONSTRAINT FK_DLOG_J_R_R_JOURNAL_DLOG_DIA FOREIGN KEY ("DIARY_ID")
      REFERENCES "DLOG_DIARY" ("DIARY_ID");

ALTER TABLE "DLOG_J_REPLY"
   ADD CONSTRAINT FK_DLOG_J_R_R_SITE_J__DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_J_REPLY"
   ADD CONSTRAINT FK_DLOG_J_R_R_USER_J__DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_LINK"
   ADD CONSTRAINT FK_DLOG_LIN_R_SITE_LI_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_MESSAGE"
   ADD CONSTRAINT FK_DLOG_MES_R_MSG_REC_DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_MUSIC"
   ADD CONSTRAINT FK_DLOG_MUS_R_MUSIC_B_DLOG_MUS FOREIGN KEY ("MUSIC_BOX_ID")
      REFERENCES "DLOG_MUSICBOX" ("MUSIC_BOX_ID");

ALTER TABLE "DLOG_MUSIC"
   ADD CONSTRAINT FK_DLOG_MUS_R_RECOMME_DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_MUSIC"
   ADD CONSTRAINT FK_DLOG_MUS_R_SITE_MU_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_MUSICBOX"
   ADD CONSTRAINT FK_DLOG_MUS_R_SITE_MB_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_P_REPLY"
   ADD CONSTRAINT FK_DLOG_P_R_R_PHOTO_R_DLOG_PHO FOREIGN KEY ("PHOTO_ID")
      REFERENCES "DLOG_PHOTO" ("PHOTO_ID");

ALTER TABLE "DLOG_P_REPLY"
   ADD CONSTRAINT FK_DLOG_P_R_R_SITE_P__DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_P_REPLY"
   ADD CONSTRAINT FK_DLOG_P_R_R_USER_P__DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_PHOTO"
   ADD CONSTRAINT FK_DLOG_PHO_R_ALBUM_P_DLOG_ALB FOREIGN KEY ("ALBUM_ID")
      REFERENCES "DLOG_ALBUM" ("ALBUM_ID");

ALTER TABLE "DLOG_PHOTO"
   ADD CONSTRAINT FK_DLOG_PHO_R_PHOTO_O_DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_PHOTO"
   ADD CONSTRAINT FK_DLOG_PHO_R_SITE_PH_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_SITE"
   ADD CONSTRAINT FK_DLOG_SIT_R_SITE_TY_DLOG_TYP FOREIGN KEY ("DLOG_TYPE_ID")
      REFERENCES "DLOG_TYPE" ("DLOG_TYPE_ID");

ALTER TABLE "DLOG_SITE"
   ADD CONSTRAINT FK_DLOG_SIT_R_USER_SI_DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_SITE_STAT"
   ADD CONSTRAINT FK_DLOG_SIT_R_SITE_ST_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_T_REPLY"
   ADD CONSTRAINT FK_DLOG_T_R_R_SITE_T__DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_T_REPLY"
   ADD CONSTRAINT FK_DLOG_T_R_R_TOPIC_R_DLOG_TOP FOREIGN KEY ("TOPIC_ID")
      REFERENCES "DLOG_TOPIC" ("TOPIC_ID");

ALTER TABLE "DLOG_T_REPLY"
   ADD CONSTRAINT FK_DLOG_T_R_R_USER_T__DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_TAG"
   ADD CONSTRAINT FK_DLOG_TAG_R_SITE_TA_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_TOPIC"
   ADD CONSTRAINT FK_DLOG_TOP_R_FORUM_T_DLOG_FOR FOREIGN KEY ("FORUM_ID")
      REFERENCES "DLOG_FORUM" ("FORUM_ID");

ALTER TABLE "DLOG_TOPIC"
   ADD CONSTRAINT FK_DLOG_TOP_R_SITE_TO_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

ALTER TABLE "DLOG_TOPIC"
   ADD CONSTRAINT FK_DLOG_TOP_R_USER_TO_DLOG_USE FOREIGN KEY ("USERID")
      REFERENCES "DLOG_USER" ("USERID");

ALTER TABLE "DLOG_TYPE"
   ADD CONSTRAINT FK_DLOG_TYP_R_SITE_TY_DLOG_TYP FOREIGN KEY ("DLO_DLOG_TYPE_ID")
      REFERENCES "DLOG_TYPE" ("DLOG_TYPE_ID");

ALTER TABLE "DLOG_USER"
   ADD CONSTRAINT FK_DLOG_USE_R_SITE_US_DLOG_SIT FOREIGN KEY ("SITE_ID")
      REFERENCES "DLOG_SITE" ("SITE_ID");

