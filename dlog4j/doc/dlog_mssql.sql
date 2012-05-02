/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2006-8-28 11:54:58                           */
/*==============================================================*/


/*==============================================================*/
/* Table: dlog_album                                            */
/*==============================================================*/
create table dlog_album (
   album_id             numeric              identity,
   photo_id             numeric              null,
   dlo_album_id         numeric              null,
   site_id              numeric              not null,
   dlog_type_id         numeric              null,
   album_name           varchar(40)          not null,
   album_desc           varchar(200)         null,
   photo_count          int                  not null,
   album_type           int                  not null,
   verifycode           varchar(20)          null,
   sort_order           int                  not null,
   create_time          datetime             not null,
   constraint pk_dlog_album primary key  (album_id)
)
go


/*==============================================================*/
/* Index: r_site_album_fk                                       */
/*==============================================================*/
create   index r_site_album_fk on dlog_album (
site_id
)
go


/*==============================================================*/
/* Index: r_album_fk                                            */
/*==============================================================*/
create   index r_album_fk on dlog_album (
dlo_album_id
)
go


/*==============================================================*/
/* Index: r_album_type_fk                                       */
/*==============================================================*/
create   index r_album_type_fk on dlog_album (
dlog_type_id
)
go


/*==============================================================*/
/* Index: r_album_cover_fk                                      */
/*==============================================================*/
create   index r_album_cover_fk on dlog_album (
photo_id
)
go


/*==============================================================*/
/* Table: dlog_blocked_ip                                       */
/*==============================================================*/
create table dlog_blocked_ip (
   blocked_ip_id        numeric              identity,
   site_id              numeric              null,
   ip_addr              int                  not null,
   s_ip_addr            varchar(16)          not null,
   ip_mask              int                  not null,
   s_ip_mask            varchar(16)          not null,
   blocked_type         smallint             not null,
   blocked_time         datetime             not null,
   status               smallint             not null,
   constraint pk_dlog_blocked_ip primary key  (blocked_ip_id)
)
go


/*==============================================================*/
/* Index: r_site_blocked_ip_fk                                  */
/*==============================================================*/
create   index r_site_blocked_ip_fk on dlog_blocked_ip (
site_id
)
go


/*==============================================================*/
/* Table: dlog_bookmark                                         */
/*==============================================================*/
create table dlog_bookmark (
   mark_id              numeric              identity,
   userid               numeric              not null,
   site_id              numeric              not null,
   parent_id            int                  not null,
   parent_type          smallint             not null,
   title                varchar(200)         null,
   url                  varchar(200)         null,
   create_time          datetime             not null,
   constraint pk_dlog_bookmark primary key  (mark_id)
)
go


/*==============================================================*/
/* Index: r_user_mark_fk                                        */
/*==============================================================*/
create   index r_user_mark_fk on dlog_bookmark (
userid
)
go


/*==============================================================*/
/* Index: r_site_bookmark_fk                                    */
/*==============================================================*/
create   index r_site_bookmark_fk on dlog_bookmark (
site_id
)
go


/*==============================================================*/
/* Table: dlog_bulletin                                         */
/*==============================================================*/
create table dlog_bulletin (
   bulletin_id          numeric              identity,
   site_id              numeric              not null,
   bulletin_type        int                  not null,
   pub_time             datetime             not null,
   status               smallint             not null,
   title                varchar(200)         not null,
   content              text                 not null,
   constraint pk_dlog_bulletin primary key  (bulletin_id)
)
go


/*==============================================================*/
/* Index: r_site_bulletin_fk                                    */
/*==============================================================*/
create   index r_site_bulletin_fk on dlog_bulletin (
site_id
)
go


/*==============================================================*/
/* Table: dlog_catalog                                          */
/*==============================================================*/
create table dlog_catalog (
   catalog_id           numeric              identity,
   dlog_type_id         numeric              null,
   site_id              numeric              not null,
   catalog_name         varchar(20)          not null,
   catalog_desc         varchar(200)         null,
   create_time          datetime             not null,
   article_count        int                  not null,
   catalog_type         smallint             not null,
   verifycode           varchar(20)          null,
   sort_order           int                  not null,
   constraint pk_dlog_catalog primary key  (catalog_id)
)
go


/*==============================================================*/
/* Index: r_site_catalog_fk                                     */
/*==============================================================*/
create   index r_site_catalog_fk on dlog_catalog (
site_id
)
go


/*==============================================================*/
/* Index: r_catalog_type_fk                                     */
/*==============================================================*/
create   index r_catalog_type_fk on dlog_catalog (
dlog_type_id
)
go


/*==============================================================*/
/* Table: dlog_catalog_perm                                     */
/*==============================================================*/
create table dlog_catalog_perm (
   catalog_id           numeric              not null,
   userid               numeric              not null,
   user_role            int                  not null,
   constraint pk_dlog_catalog_perm primary key  (catalog_id, userid)
)
go


/*==============================================================*/
/* Index: dlog_catalog_perm_fk                                  */
/*==============================================================*/
create   index dlog_catalog_perm_fk on dlog_catalog_perm (
catalog_id
)
go


/*==============================================================*/
/* Index: dlog_catalog_perm2_fk                                 */
/*==============================================================*/
create   index dlog_catalog_perm2_fk on dlog_catalog_perm (
userid
)
go


/*==============================================================*/
/* Table: dlog_comments                                         */
/*==============================================================*/
create table dlog_comments (
   comment_id           numeric              identity,
   dlo_comment_id       numeric              null,
   site_id              numeric              null,
   entity_id            int                  not null,
   entity_type          int                  not null,
   client_ip            varchar(16)          not null,
   client_type          smallint             not null,
   client_user_agent    varchar(100)         null,
   author_id            int                  not null,
   author               varchar(20)          not null,
   author_email         varchar(50)          null,
   author_url           varchar(100)         null,
   title                varchar(200)         not null,
   content              text                 not null,
   content_format       smallint             not null,
   comment_time         datetime             not null,
   comment_flag         smallint             not null,
   comment_status       smallint             not null,
   constraint pk_dlog_comments primary key  (comment_id)
)
go


/*==============================================================*/
/* Index: r_sub_comment_fk                                      */
/*==============================================================*/
create   index r_sub_comment_fk on dlog_comments (
dlo_comment_id
)
go


/*==============================================================*/
/* Index: r_site_comment_fk                                     */
/*==============================================================*/
create   index r_site_comment_fk on dlog_comments (
site_id
)
go


/*==============================================================*/
/* Table: dlog_config                                           */
/*==============================================================*/
create table dlog_config (
   config_id            numeric              identity,
   site_id              numeric              null,
   config_name          varchar(20)          not null,
   int_value            int                  null,
   string_value         varchar(100)         null,
   date_value           datetime             null,
   time_value           datetime             null,
   timestamp_value      datetime             null,
   last_update          timestamp            not null,
   constraint pk_dlog_config primary key  (config_id)
)
go


/*==============================================================*/
/* Index: r_site_config_fk                                      */
/*==============================================================*/
create   index r_site_config_fk on dlog_config (
site_id
)
go


/*==============================================================*/
/* Table: dlog_diary                                            */
/*==============================================================*/
create table dlog_diary (
   diary_id             numeric              identity,
   userid               numeric              not null,
   site_id              numeric              not null,
   catalog_id           numeric              not null,
   author               varchar(20)          not null,
   author_url           varchar(100)         null,
   title                varchar(200)         not null,
   content              text                 not null,
   diary_size           int                  not null,
   refer                varchar(100)         null,
   weather              varchar(20)          not null,
   mood_level           smallint             not null,
   tags                 varchar(100)         null,
   bgsound              int                  null,
   reply_count          int                  not null,
   view_count           int                  not null,
   tb_count             int                  not null,
   client_type          smallint             not null,
   client_ip            varchar(16)          not null,
   client_user_agent    varchar(100)         null,
   write_time           datetime             not null,
   last_read_time       datetime             null,
   last_reply_time      datetime             null,
   modify_time          datetime             null,
   reply_notify         smallint             not null,
   diary_type           smallint             not null,
   locked               smallint             not null,
   status               smallint             not null,
   constraint pk_dlog_diary primary key  (diary_id)
)
go


/*==============================================================*/
/* Index: r_site_journal_fk                                     */
/*==============================================================*/
create   index r_site_journal_fk on dlog_diary (
site_id
)
go


/*==============================================================*/
/* Index: r_catalog_diary_fk                                    */
/*==============================================================*/
create   index r_catalog_diary_fk on dlog_diary (
catalog_id
)
go


/*==============================================================*/
/* Index: r_user_journal_fk                                     */
/*==============================================================*/
create   index r_user_journal_fk on dlog_diary (
userid
)
go


/*==============================================================*/
/* Table: dlog_external_refer                                   */
/*==============================================================*/
create table dlog_external_refer (
   refer_id             numeric              identity,
   site_id              numeric              null,
   ref_id               int                  not null,
   ref_type             smallint             not null,
   refer_host           varchar(50)          null,
   refer_url            varchar(250)         not null,
   client_ip            varchar(16)          not null,
   refer_time           datetime             not null,
   constraint pk_dlog_external_refer primary key  (refer_id)
)
go


/*==============================================================*/
/* Index: r_site_refer_fk                                       */
/*==============================================================*/
create   index r_site_refer_fk on dlog_external_refer (
site_id
)
go


/*==============================================================*/
/* Table: dlog_fck_upload_file                                  */
/*==============================================================*/
create table dlog_fck_upload_file (
   fck_file_id          numeric              identity,
   userid               numeric              not null,
   site_id              numeric              null,
   upload_time          datetime             not null,
   session_id           varchar(100)         not null,
   ref_id               int                  not null,
   ref_type             smallint             not null,
   save_path            varchar(255)         not null,
   file_uri             varchar(100)         not null,
   file_type            int                  not null,
   file_size            int                  not null,
   constraint pk_dlog_fck_upload_file primary key  (fck_file_id)
)
go


/*==============================================================*/
/* Index: r_site_file_fk                                        */
/*==============================================================*/
create   index r_site_file_fk on dlog_fck_upload_file (
site_id
)
go


/*==============================================================*/
/* Index: r_user_upload_fk                                      */
/*==============================================================*/
create   index r_user_upload_fk on dlog_fck_upload_file (
userid
)
go


/*==============================================================*/
/* Table: dlog_forum                                            */
/*==============================================================*/
create table dlog_forum (
   forum_id             numeric              identity,
   site_id              numeric              not null,
   dlog_type_id         numeric              null,
   forum_name           varchar(40)          not null,
   forum_desc           varchar(200)         null,
   forum_type           int                  not null,
   create_time          datetime             not null,
   modify_time          datetime             null,
   last_time            datetime             null,
   last_user_id         int                  null,
   last_user_name       varchar(50)          null,
   last_topic_id        int                  null,
   sort_order           int                  not null,
   topic_count          int                  not null,
   forum_option         int                  not null,
   status               smallint             not null,
   constraint pk_dlog_forum primary key  (forum_id)
)
go


/*==============================================================*/
/* Index: r_site_forum_fk                                       */
/*==============================================================*/
create   index r_site_forum_fk on dlog_forum (
site_id
)
go


/*==============================================================*/
/* Index: r_forum_type_fk                                       */
/*==============================================================*/
create   index r_forum_type_fk on dlog_forum (
dlog_type_id
)
go


/*==============================================================*/
/* Table: dlog_friend                                           */
/*==============================================================*/
create table dlog_friend (
   user_id              int                  not null,
   friend_id            int                  not null,
   friend_type          int                  not null,
   friend_role          int                  not null,
   add_time             datetime             not null,
   constraint pk_dlog_friend primary key  (user_id, friend_id)
)
go


/*==============================================================*/
/* Table: dlog_guestbook                                        */
/*==============================================================*/
create table dlog_guestbook (
   guest_book_id        numeric              identity,
   userid               numeric              not null,
   site_id              numeric              not null,
   content              text                 not null,
   client_type          smallint             not null,
   client_ip            varchar(16)          not null,
   client_user_agent    varchar(100)         null,
   create_time          datetime             not null,
   reply_content        text                 null,
   reply_time           datetime             null,
   constraint pk_dlog_guestbook primary key  (guest_book_id)
)
go


/*==============================================================*/
/* Index: r_site_guest_fk                                       */
/*==============================================================*/
create   index r_site_guest_fk on dlog_guestbook (
site_id
)
go


/*==============================================================*/
/* Index: r_user_liuyan_fk                                      */
/*==============================================================*/
create   index r_user_liuyan_fk on dlog_guestbook (
userid
)
go


/*==============================================================*/
/* Table: dlog_j_reply                                          */
/*==============================================================*/
create table dlog_j_reply (
   j_reply_id           numeric              identity,
   site_id              numeric              not null,
   userid               numeric              null,
   diary_id             numeric              not null,
   author               varchar(20)          not null,
   author_url           varchar(100)         null,
   author_email         varchar(50)          null,
   client_type          smallint             not null,
   client_ip            varchar(16)          not null,
   client_user_agent    varchar(100)         null,
   owner_only           int                  not null,
   content              text                 not null,
   write_time           datetime             not null,
   status               smallint             not null,
   constraint pk_dlog_j_reply primary key  (j_reply_id)
)
go


/*==============================================================*/
/* Index: r_site_j_reply_fk                                     */
/*==============================================================*/
create   index r_site_j_reply_fk on dlog_j_reply (
site_id
)
go


/*==============================================================*/
/* Index: r_journal_reply_fk                                    */
/*==============================================================*/
create   index r_journal_reply_fk on dlog_j_reply (
diary_id
)
go


/*==============================================================*/
/* Index: r_user_j_reply_fk                                     */
/*==============================================================*/
create   index r_user_j_reply_fk on dlog_j_reply (
userid
)
go


/*==============================================================*/
/* Table: dlog_link                                             */
/*==============================================================*/
create table dlog_link (
   linkid               numeric              identity,
   site_id              numeric              not null,
   link_title           varchar(40)          not null,
   link_url             varchar(200)         not null,
   link_type            smallint             not null,
   sort_order           int                  not null,
   create_time          datetime             not null,
   status               smallint             not null,
   constraint pk_dlog_link primary key  (linkid)
)
go


/*==============================================================*/
/* Index: r_site_link_fk                                        */
/*==============================================================*/
create   index r_site_link_fk on dlog_link (
site_id
)
go


/*==============================================================*/
/* Table: dlog_message                                          */
/*==============================================================*/
create table dlog_message (
   msgid                numeric              identity,
   userid               numeric              null,
   from_user_id         int                  not null,
   content              text                 not null,
   send_time            datetime             not null,
   expire_time          datetime             null,
   read_time            datetime             null,
   status               smallint             not null,
   constraint pk_dlog_message primary key  (msgid)
)
go


/*==============================================================*/
/* Index: r_msg_receiver_fk                                     */
/*==============================================================*/
create   index r_msg_receiver_fk on dlog_message (
userid
)
go


/*==============================================================*/
/* Table: dlog_music                                            */
/*==============================================================*/
create table dlog_music (
   music_id             numeric              identity,
   music_box_id         numeric              null,
   userid               numeric              null,
   site_id              numeric              not null,
   music_title          varchar(100)         not null,
   music_word           text                 null,
   album                varchar(100)         null,
   singer               varchar(50)          null,
   url                  varchar(200)         null,
   create_time          datetime             not null,
   view_count           int                  not null,
   music_type           int                  not null,
   status               smallint             not null,
   constraint pk_dlog_music primary key  (music_id)
)
go


/*==============================================================*/
/* Index: r_site_music_fk                                       */
/*==============================================================*/
create   index r_site_music_fk on dlog_music (
site_id
)
go


/*==============================================================*/
/* Index: r_music_box_fk                                        */
/*==============================================================*/
create   index r_music_box_fk on dlog_music (
music_box_id
)
go


/*==============================================================*/
/* Index: r_recommend_fk                                        */
/*==============================================================*/
create   index r_recommend_fk on dlog_music (
userid
)
go


/*==============================================================*/
/* Table: dlog_musicbox                                         */
/*==============================================================*/
create table dlog_musicbox (
   music_box_id         numeric              identity,
   site_id              numeric              not null,
   box_name             varchar(40)          not null,
   box_desc             varchar(100)         null,
   music_count          int                  not null,
   create_time          datetime             not null,
   sort_order           int                  not null,
   constraint pk_dlog_musicbox primary key  (music_box_id)
)
go


/*==============================================================*/
/* Index: r_site_mbox_fk                                        */
/*==============================================================*/
create   index r_site_mbox_fk on dlog_musicbox (
site_id
)
go


/*==============================================================*/
/* Table: dlog_my_blacklist                                     */
/*==============================================================*/
create table dlog_my_blacklist (
   my_user_id           int                  not null,
   other_user_id        int                  not null,
   bl_type              int                  not null,
   add_time             datetime             not null,
   constraint pk_dlog_my_blacklist primary key  (my_user_id, other_user_id)
)
go


/*==============================================================*/
/* Table: dlog_p_reply                                          */
/*==============================================================*/
create table dlog_p_reply (
   p_reply_id           numeric              identity,
   site_id              numeric              not null,
   photo_id             numeric              not null,
   userid               numeric              null,
   author               varchar(20)          not null,
   author_url           varchar(100)         null,
   author_email         varchar(50)          null,
   client_type          smallint             not null,
   client_ip            varchar(16)          not null,
   client_user_agent    varchar(100)         null,
   owner_only           int                  not null,
   content              text                 not null,
   write_time           datetime             not null,
   status               smallint             not null,
   constraint pk_dlog_p_reply primary key  (p_reply_id)
)
go


/*==============================================================*/
/* Index: r_photo_reply_fk                                      */
/*==============================================================*/
create   index r_photo_reply_fk on dlog_p_reply (
photo_id
)
go


/*==============================================================*/
/* Index: r_site_p_reply_fk                                     */
/*==============================================================*/
create   index r_site_p_reply_fk on dlog_p_reply (
site_id
)
go


/*==============================================================*/
/* Index: r_user_p_reply_fk                                     */
/*==============================================================*/
create   index r_user_p_reply_fk on dlog_p_reply (
userid
)
go


/*==============================================================*/
/* Table: dlog_photo                                            */
/*==============================================================*/
create table dlog_photo (
   photo_id             numeric              identity,
   site_id              numeric              not null,
   album_id             numeric              not null,
   userid               numeric              not null,
   photo_name           varchar(40)          not null,
   photo_desc           text                 null,
   file_name            varchar(100)         not null,
   photo_url            varchar(100)         not null,
   preview_url          varchar(100)         not null,
   tags                 varchar(100)         null,
   p_year               int                  not null,
   p_month              smallint             not null,
   p_date               smallint             not null,
   width                int                  not null,
   height               int                  not null,
   photo_size           bigint               not null,
   color_bit            int                  not null,
   exif_manufacturer    varchar(50)          null,
   exif_model           varchar(50)          null,
   exif_iso             int                  not null,
   exif_aperture        varchar(20)          null,
   exif_shutter         varchar(20)          null,
   exif_exposure_bias   varchar(20)          null,
   exif_exposure_time   varchar(20)          null,
   exif_focal_length    varchar(20)          null,
   exif_color_space     varchar(20)          null,
   reply_count          int                  not null,
   view_count           int                  not null,
   create_time          datetime             not null,
   modify_time          datetime             null,
   last_reply_time      datetime             null,
   photo_type           int                  not null,
   locked               smallint             not null,
   photo_status         smallint             not null,
   constraint pk_dlog_photo primary key  (photo_id)
)
go


/*==============================================================*/
/* Index: r_album_photo_fk                                      */
/*==============================================================*/
create   index r_album_photo_fk on dlog_photo (
album_id
)
go


/*==============================================================*/
/* Index: r_site_photo_fk                                       */
/*==============================================================*/
create   index r_site_photo_fk on dlog_photo (
site_id
)
go


/*==============================================================*/
/* Index: r_photo_owner_fk                                      */
/*==============================================================*/
create   index r_photo_owner_fk on dlog_photo (
userid
)
go


/*==============================================================*/
/* Table: dlog_site                                             */
/*==============================================================*/
create table dlog_site (
   site_id              numeric              identity,
   userid               numeric              not null,
   dlog_type_id         numeric              null,
   site_name            varchar(20)          not null,
   site_c_name          varchar(50)          not null,
   site_url             varchar(100)         null,
   site_title           varchar(100)         null,
   site_detail          varchar(250)         null,
   site_icp             varchar(20)          null,
   site_logo            varchar(50)          null,
   site_css             varchar(50)          null,
   site_layout          varchar(50)          null,
   site_lang            varchar(10)          null,
   site_flag            int                  not null,
   create_time          datetime             not null,
   last_time            datetime             null,
   expired_time         datetime             null,
   last_exp_time        datetime             null,
   access_mode          smallint             not null,
   access_code          varchar(50)          null,
   diary_status         smallint             not null,
   photo_status         smallint             not null,
   music_status         smallint             not null,
   forum_status         smallint             not null,
   guestbook_status     smallint             not null,
   diary_cname          varchar(16)          null,
   photo_cname          varchar(16)          null,
   music_cname          varchar(16)          null,
   bbs_cname            varchar(16)          null,
   guestbook_cname      varchar(16)          null,
   photo_space_total    int                  not null,
   photo_space_used     int                  not null,
   diary_space_total    int                  not null,
   diary_space_used     int                  not null,
   media_space_total    int                  not null,
   media_space_used     int                  not null,
   site_type            int                  not null,
   site_level           int                  not null,
   status               smallint             not null,
   constraint pk_dlog_site primary key  (site_id)
)
go


/*==============================================================*/
/* Index: r_user_site_fk                                        */
/*==============================================================*/
create   index r_user_site_fk on dlog_site (
userid
)
go


/*==============================================================*/
/* Index: r_site_type_fk                                        */
/*==============================================================*/
create   index r_site_type_fk on dlog_site (
dlog_type_id
)
go


/*==============================================================*/
/* Table: dlog_site_stat                                        */
/*==============================================================*/
create table dlog_site_stat (
   site_stat_id         numeric              identity,
   site_id              numeric              null,
   stat_date            int                  not null,
   uv_count             int                  not null,
   pv_count             int                  not null,
   v_source             int                  not null,
   update_time          datetime             not null,
   constraint pk_dlog_site_stat primary key  (site_stat_id)
)
go


/*==============================================================*/
/* Index: r_site_stat_fk                                        */
/*==============================================================*/
create   index r_site_stat_fk on dlog_site_stat (
site_id
)
go


/*==============================================================*/
/* Table: dlog_t_reply                                          */
/*==============================================================*/
create table dlog_t_reply (
   t_reply_id           numeric              identity,
   topic_id             numeric              not null,
   userid               numeric              not null,
   site_id              numeric              not null,
   title                varchar(200)         not null,
   content              text                 not null,
   write_time           datetime             null,
   status               smallint             null,
   client_ip            varchar(16)          null,
   client_type          smallint             null,
   client_user_agent    varchar(100)         null,
   constraint pk_dlog_t_reply primary key  (t_reply_id)
)
go


/*==============================================================*/
/* Index: r_site_t_reply_fk                                     */
/*==============================================================*/
create   index r_site_t_reply_fk on dlog_t_reply (
site_id
)
go


/*==============================================================*/
/* Index: r_topic_reply_fk                                      */
/*==============================================================*/
create   index r_topic_reply_fk on dlog_t_reply (
topic_id
)
go


/*==============================================================*/
/* Index: r_user_t_reply_fk                                     */
/*==============================================================*/
create   index r_user_t_reply_fk on dlog_t_reply (
userid
)
go


/*==============================================================*/
/* Table: dlog_tag                                              */
/*==============================================================*/
create table dlog_tag (
   tag_id               numeric              identity,
   site_id              numeric              not null,
   ref_id               int                  not null,
   ref_type             smallint             not null,
   tag_name             varchar(20)          not null,
   constraint pk_dlog_tag primary key  (tag_id)
)
go


/*==============================================================*/
/* Index: r_site_tag_fk                                         */
/*==============================================================*/
create   index r_site_tag_fk on dlog_tag (
site_id
)
go

create   index idx_tagname on dlog_tag (
   tag_name
)
go


/*==============================================================*/
/* Table: dlog_topic                                            */
/*==============================================================*/
create table dlog_topic (
   topic_id             numeric              identity,
   site_id              numeric              not null,
   userid               numeric              not null,
   forum_id             numeric              not null,
   username            varchar(40)          not null,
   create_time          datetime             not null,
   modify_time          datetime             null,
   title                varchar(200)         not null,
   content              text                 not null,
   tags                 varchar(100)         null,
   last_reply_time      datetime             null,
   last_reply_id        int                  null,
   last_user_id         int                  null,
   last_user_name       varchar(50)          null,
   reply_count          int                  not null,
   view_count           int                  not null,
   locked               smallint             not null,
   topic_type           int                  not null,
   status               smallint             not null,
   client_type          smallint             not null,
   client_ip            varchar(16)          not null,
   client_user_agent    varchar(100)         null,
   constraint pk_dlog_topic primary key  (topic_id)
)
go


/*==============================================================*/
/* Index: r_site_topic_fk                                       */
/*==============================================================*/
create   index r_site_topic_fk on dlog_topic (
site_id
)
go


/*==============================================================*/
/* Index: r_forum_topic_fk                                      */
/*==============================================================*/
create   index r_forum_topic_fk on dlog_topic (
forum_id
)
go


/*==============================================================*/
/* Index: r_user_topic_fk                                       */
/*==============================================================*/
create   index r_user_topic_fk on dlog_topic (
userid
)
go


/*==============================================================*/
/* Table: dlog_trackback                                        */
/*==============================================================*/
create table dlog_trackback (
   track_id             numeric              identity,
   parent_id            int                  not null,
   parent_type          smallint             not null,
   refurl               varchar(100)         not null,
   title                varchar(200)         null,
   excerpt              varchar(200)         null,
   blog_name            varchar(50)          not null,
   remote_addr          char(15)             not null,
   track_time           datetime             not null,
   constraint pk_dlog_trackback primary key  (track_id)
)
go


/*==============================================================*/
/* Table: dlog_type                                             */
/*==============================================================*/
create table dlog_type (
   dlog_type_id         numeric              identity,
   dlo_dlog_type_id     numeric              null,
   type_name            varchar(20)          not null,
   sort_order           int                  not null,
   constraint pk_dlog_type primary key  (dlog_type_id)
)
go


/*==============================================================*/
/* Index: r_site_type_tree_fk                                   */
/*==============================================================*/
create   index r_site_type_tree_fk on dlog_type (
dlo_dlog_type_id
)
go


/*==============================================================*/
/* Table: dlog_user                                             */
/*==============================================================*/
create table dlog_user (
   userid               numeric              identity,
   site_id              numeric              null,
   own_site_id          int                  not null,
   username             varchar(40)          not null,
   password             varchar(50)          not null,
   user_role            int                  not null,
   nickname             varchar(50)          not null,
   sex                  smallint             not null,
   birth                datetime             null,
   email                varchar(50)          null,
   homepage             varchar(50)          null,
   qq                   varchar(16)          null,
   msn                  varchar(50)          null,
   mobile               varchar(16)          null,
   nation               varchar(40)          null,
   province             varchar(40)          null,
   city                 varchar(40)          null,
   industry             varchar(40)          null,
   company              varchar(80)          null,
   address              varchar(200)         null,
   job                  varchar(40)          null,
   fax                  varchar(20)          null,
   tel                  varchar(20)          null,
   zip                  varchar(12)          null,
   portrait             varchar(100)         null,
   resume               varchar(200)         null,
   regtime              datetime             not null,
   last_time            datetime             null,
   last_ip              varchar(16)          null,
   keep_days            int                  not null,
   online_status        int                  not null,
   user_level           int                  not null,
   status               smallint             not null,
   article_count        int                  not null,
   article_reply_count  int                  not null,
   topic_count          int                  not null,
   topic_reply_count    int                  not null,
   photo_count          int                  not null,
   photo_reply_count    int                  not null,
   guestbook_count      int                  not null,
   bookmark_count       int                  not null,
   constraint pk_dlog_user primary key  (userid)
)
go


/*==============================================================*/
/* Index: r_site_user_fk                                        */
/*==============================================================*/
create   index r_site_user_fk on dlog_user (
site_id
)
go


alter table dlog_album
   add constraint fk_dlog_alb_r_album_dlog_alb foreign key (dlo_album_id)
      references dlog_album (album_id)
go


alter table dlog_album
   add constraint fk_dlog_alb_r_album_c_dlog_pho foreign key (photo_id)
      references dlog_photo (photo_id)
go


alter table dlog_album
   add constraint fk_dlog_alb_r_album_t_dlog_typ foreign key (dlog_type_id)
      references dlog_type (dlog_type_id)
go


alter table dlog_album
   add constraint fk_dlog_alb_r_site_al_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_blocked_ip
   add constraint fk_dlog_blo_r_site_bl_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_bookmark
   add constraint fk_dlog_boo_r_site_bo_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_bookmark
   add constraint fk_dlog_boo_r_user_ma_dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_bulletin
   add constraint fk_dlog_bul_r_site_bu_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_catalog
   add constraint fk_dlog_cat_r_catalog_dlog_typ foreign key (dlog_type_id)
      references dlog_type (dlog_type_id)
go


alter table dlog_catalog
   add constraint fk_dlog_cat_r_site_ca_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_catalog_perm
   add constraint fk_dlog_cat_dlog_cata_dlog_cat foreign key (catalog_id)
      references dlog_catalog (catalog_id)
go


alter table dlog_catalog_perm
   add constraint fk_dlog_cat_dlog_cata_dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_comments
   add constraint fk_dlog_com_r_site_co_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_comments
   add constraint fk_dlog_com_r_sub_com_dlog_com foreign key (dlo_comment_id)
      references dlog_comments (comment_id)
go


alter table dlog_config
   add constraint fk_dlog_con_r_site_co_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_diary
   add constraint fk_dlog_dia_r_catalog_dlog_cat foreign key (catalog_id)
      references dlog_catalog (catalog_id)
go


alter table dlog_diary
   add constraint fk_dlog_dia_r_site_jo_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_diary
   add constraint fk_dlog_dia_r_user_jo_dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_external_refer
   add constraint fk_dlog_ext_r_site_re_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_fck_upload_file
   add constraint fk_dlog_fck_r_site_fi_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_fck_upload_file
   add constraint fk_dlog_fck_r_user_up_dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_forum
   add constraint fk_dlog_for_r_forum_t_dlog_typ foreign key (dlog_type_id)
      references dlog_type (dlog_type_id)
go


alter table dlog_forum
   add constraint fk_dlog_for_r_site_fo_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_guestbook
   add constraint fk_dlog_gue_r_site_gu_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_guestbook
   add constraint fk_dlog_gue_r_user_li_dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_j_reply
   add constraint fk_dlog_j_r_r_journal_dlog_dia foreign key (diary_id)
      references dlog_diary (diary_id)
go


alter table dlog_j_reply
   add constraint fk_dlog_j_r_r_site_j__dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_j_reply
   add constraint fk_dlog_j_r_r_user_j__dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_link
   add constraint fk_dlog_lin_r_site_li_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_message
   add constraint fk_dlog_mes_r_msg_rec_dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_music
   add constraint fk_dlog_mus_r_music_b_dlog_mus foreign key (music_box_id)
      references dlog_musicbox (music_box_id)
go


alter table dlog_music
   add constraint fk_dlog_mus_r_recomme_dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_music
   add constraint fk_dlog_mus_r_site_mu_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_musicbox
   add constraint fk_dlog_mus_r_site_mb_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_p_reply
   add constraint fk_dlog_p_r_r_photo_r_dlog_pho foreign key (photo_id)
      references dlog_photo (photo_id)
go


alter table dlog_p_reply
   add constraint fk_dlog_p_r_r_site_p__dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_p_reply
   add constraint fk_dlog_p_r_r_user_p__dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_photo
   add constraint fk_dlog_pho_r_album_p_dlog_alb foreign key (album_id)
      references dlog_album (album_id)
go


alter table dlog_photo
   add constraint fk_dlog_pho_r_photo_o_dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_photo
   add constraint fk_dlog_pho_r_site_ph_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_site
   add constraint fk_dlog_sit_r_site_ty_dlog_typ foreign key (dlog_type_id)
      references dlog_type (dlog_type_id)
go


alter table dlog_site
   add constraint fk_dlog_sit_r_user_si_dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_t_reply
   add constraint fk_dlog_t_r_r_site_t__dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_t_reply
   add constraint fk_dlog_t_r_r_topic_r_dlog_top foreign key (topic_id)
      references dlog_topic (topic_id)
go


alter table dlog_t_reply
   add constraint fk_dlog_t_r_r_user_t__dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_tag
   add constraint fk_dlog_tag_r_site_ta_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_topic
   add constraint fk_dlog_top_r_forum_t_dlog_for foreign key (forum_id)
      references dlog_forum (forum_id)
go


alter table dlog_topic
   add constraint fk_dlog_top_r_site_to_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


alter table dlog_topic
   add constraint fk_dlog_top_r_user_to_dlog_use foreign key (userid)
      references dlog_user (userid)
go


alter table dlog_type
   add constraint fk_dlog_typ_r_site_ty_dlog_typ foreign key (dlo_dlog_type_id)
      references dlog_type (dlog_type_id)
go


alter table dlog_user
   add constraint fk_dlog_use_r_site_us_dlog_sit foreign key (site_id)
      references dlog_site (site_id)
go


