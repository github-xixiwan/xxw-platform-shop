create table if not exists area
(
    area_id     bigint auto_increment
    primary key,
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    area_name   varchar(50)                        not null comment '地址',
    parent_id   bigint                             not null comment '上级地址',
    level       int                                not null comment '等级（从1开始）'
    )
    comment '省市区地区信息';

create index parent_id
    on area (parent_id)
    comment '上级id';

create table if not exists attach_file
(
    file_id              bigint auto_increment
    primary key,
    create_time          datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time          datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    file_path            varchar(255)                       null comment '文件路径',
    file_type            varchar(20)                        null comment '文件类型',
    file_name            varchar(255)                       null comment '文件名',
    file_size            int                                null comment '文件大小',
    shop_id              bigint                             null comment '店铺id',
    type                 tinyint                            null comment '文件 1:图片 2:视频 3:文件',
    attach_file_group_id bigint   default 0                 null comment '文件分组id'
    )
    comment '上传文件记录表';

create table if not exists attach_file_group
(
    attach_file_group_id bigint auto_increment
    primary key,
    create_time          datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time          datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    shop_id              bigint                             not null comment '店铺id',
    name                 varchar(6)                         not null comment '分组名称'
    );

create table if not exists attr
(
    attr_id     bigint unsigned auto_increment comment 'attr id'
    primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    shop_id     bigint   default 0                 not null comment '店铺Id',
    name        varchar(20)                        null comment '属性名称',
    `desc`      varchar(255)                       null comment '属性描述',
    search_type tinyint  default 0                 not null comment '0:不需要，1:需要',
    attr_type   tinyint  default 0                 not null comment '0:销售属性，1:基本属性'
    )
    comment '属性信息' row_format = DYNAMIC;

create index idx_shop_id
    on attr (shop_id);

create table if not exists attr_category
(
    attr_category_id bigint unsigned auto_increment comment '属性与分类关联id'
    primary key,
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    category_id      bigint unsigned                    not null comment '分类id',
    attr_id          bigint                             not null comment '属性id',
    constraint uni_attrgroup_id
    unique (category_id, attr_id)
    )
    comment '属性与分类关联信息' row_format = DYNAMIC;

create index idx_attr_id
    on attr_category (attr_id);

create table if not exists attr_value
(
    attr_value_id bigint unsigned auto_increment comment '属性id'
    primary key,
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    attr_id       bigint unsigned                    not null comment '属性ID',
    value         varchar(20)                        null comment '属性值'
    )
    comment '属性值信息' row_format = DYNAMIC;

create index idx_attr
    on attr_value (attr_id);

create table if not exists auth_account
(
    uid         bigint unsigned                       not null comment '全平台用户唯一id'
    primary key,
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    username    varchar(30) default ''                not null comment '用户名',
    password    varchar(64) default ''                not null comment '密码',
    create_ip   varchar(15) default ''                not null comment '创建ip',
    status      tinyint                               not null comment '状态 1:启用 0:禁用 -1:删除',
    sys_type    tinyint                               not null comment '用户类型见SysTypeEnum 0.普通用户系统 1.商家端 2平台端',
    user_id     bigint                                not null comment '用户id',
    tenant_id   bigint                                null comment '所属租户',
    is_admin    tinyint                               null comment '是否是管理员',
    constraint uk_usertype_userid
    unique (sys_type, user_id)
    )
    comment '统一账户信息';

create index idx_username
    on auth_account (username);

create table if not exists brand
(
    brand_id     bigint unsigned auto_increment comment 'brand_id'
    primary key,
    create_time  datetime         default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    name         varchar(255)                               null comment '品牌名称',
    `desc`       varchar(255)                               null comment '品牌描述',
    img_url      varchar(255)     default ''                not null comment '品牌logo图片',
    first_letter char                                       null comment '检索首字母',
    seq          int                                        null comment '排序',
    status       tinyint unsigned default 0                 null comment '状态 1:enable, 0:disable, -1:deleted',
    constraint uni_brand_id
    unique (brand_id)
    )
    comment '品牌信息' row_format = DYNAMIC;

create table if not exists category
(
    category_id bigint unsigned auto_increment comment '分类id'
    primary key,
    create_time datetime         default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime         default CURRENT_TIMESTAMP not null comment '更新时间',
    shop_id     bigint unsigned                            not null comment '店铺id',
    parent_id   bigint unsigned  default 0                 not null comment '父ID',
    name        varchar(20)                                null comment '分类名称',
    `desc`      varchar(255)                               null comment '分类描述',
    path        varchar(255)     default ''                not null comment '分类地址{parent_id}-{child_id},...',
    status      tinyint unsigned default 0                 not null comment '状态 1:enable, 0:disable, -1:deleted',
    icon        varchar(255)                               null comment '分类图标',
    img_url     varchar(255)                               null comment '分类的显示图片',
    level       int                                        not null comment '分类层级 从0开始',
    seq         int                                        null comment '排序'
    )
    comment '分类信息' row_format = DYNAMIC;

create index idx_pid
    on category (parent_id);

create index idx_shop_id
    on category (shop_id);

create table if not exists category_brand
(
    id          bigint auto_increment
    primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    brand_id    bigint unsigned                    not null comment '品牌id',
    category_id bigint unsigned                    not null comment '分类id',
    constraint uni_brand_category_id
    unique (brand_id, category_id)
    )
    comment '品牌分类关联信息' row_format = DYNAMIC;

create index idx_category_id
    on category_brand (category_id);

create table if not exists hot_search
(
    hot_search_id bigint unsigned auto_increment comment '主键'
    primary key,
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    shop_id       bigint                             null comment '店铺ID 0为全平台热搜',
    content       varchar(255)                       not null comment '内容',
    seq           int                                null comment '顺序',
    status        tinyint  default 1                 not null comment '状态 0下线 1上线',
    title         varchar(255)                       not null comment '热搜标题'
    )
    comment '热搜';

create index idx_shop_id
    on hot_search (shop_id);

create table if not exists index_img
(
    img_id      bigint unsigned auto_increment comment '主键'
    primary key,
    create_time datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    shop_id     bigint                               not null comment '店铺ID',
    img_url     varchar(200)                         not null comment '图片',
    status      tinyint(1) default 0                 not null comment '状态 1:enable, 0:disable',
    seq         int        default 0                 not null comment '顺序',
    spu_id      bigint                               null comment '关联商品id',
    img_type    tinyint                              not null comment '图片类型 0:小程序'
    )
    comment '轮播图';

create index idx_shop_id
    on index_img (shop_id);

create index idx_spu_id
    on index_img (spu_id);

create table if not exists menu
(
    menu_id     bigint unsigned auto_increment comment '菜单id'
    primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    parent_id   bigint unsigned                    not null comment '父菜单ID，一级菜单为0',
    biz_type    tinyint                            null comment '业务类型 1 店铺菜单 2平台菜单',
    permission  varchar(255)                       null comment '权限，需要有哪个权限才能访问该菜单',
    path        varchar(255)                       null comment '路径 就像uri',
    component   varchar(255)                       null comment '1.''Layout'' 为布局，不会跳页面 2.''components-demo/tinymce'' 跳转到该页面',
    redirect    varchar(255)                       null comment '当设置 noRedirect 的时候该路由在面包屑导航中不可被点击',
    always_show tinyint                            null comment '一直显示根路由',
    hidden      tinyint                            null comment '当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1',
    name        varchar(32)                        null comment '设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题',
    title       varchar(32)                        null comment '设置该路由在侧边栏和面包屑中展示的名字',
    icon        varchar(32)                        null comment '设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon',
    no_cache    tinyint                            null comment '如果设置为true，则不会被 <keep-alive> 缓存(默认 false)',
    breadcrumb  tinyint                            null comment '如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)',
    affix       tinyint                            null comment '若果设置为true，它则会固定在tags-view中(默认 false)',
    active_menu varchar(255)                       null comment '当路由设置了该属性，则会高亮相对应的侧边栏。',
    seq         int                                null comment '排序，越小越靠前'
    )
    comment '菜单管理';

create index idx_pid
    on menu (parent_id);

create table if not exists menu_permission
(
    menu_permission_id bigint unsigned auto_increment comment '菜单资源用户id'
    primary key,
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    menu_id            bigint                             not null comment '资源关联菜单',
    biz_type           tinyint                            not null comment '业务类型 1 店铺菜单 2平台菜单',
    permission         varchar(255)                       not null comment '权限对应的编码',
    name               varchar(32)                        not null comment '资源名称',
    uri                varchar(255)                       not null comment '资源对应服务器路径',
    method             tinyint                            not null comment '请求方法 1.GET 2.POST 3.PUT 4.DELETE',
    constraint uk_permission
    unique (permission, biz_type)
    )
    comment '菜单资源';

create index idx_menuid
    on menu_permission (menu_id);

create table if not exists order_addr
(
    order_addr_id bigint unsigned auto_increment comment 'ID'
    primary key,
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    user_id       bigint                             null comment '用户ID',
    consignee     varchar(50)                        null comment '收货人',
    province_id   bigint                             null comment '省ID',
    province      varchar(100)                       null comment '省',
    city_id       bigint                             null comment '城市ID',
    city          varchar(20)                        null comment '城市',
    area_id       bigint                             null comment '区域ID',
    area          varchar(20)                        null comment '区',
    addr          varchar(1000)                      null comment '地址',
    post_code     varchar(15)                        null comment '邮编',
    mobile        varchar(20)                        null comment '手机',
    lng           decimal(12, 6)                     null comment '经度',
    lat           decimal(12, 6)                     null comment '纬度'
    )
    comment '用户订单配送地址';

create index idx_user_id
    on order_addr (user_id);

create table if not exists order_info
(
    order_id      bigint unsigned                    not null comment '订单ID'
    primary key,
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    shop_id       bigint                             null comment '店铺id',
    user_id       bigint                             not null comment '用户ID',
    delivery_type tinyint                            null comment '配送类型：无需快递',
    shop_name     varchar(255)                       null comment '店铺名称',
    total         bigint                             not null comment '总值',
    status        tinyint  default 0                 not null comment '订单状态 1:待付款 2:待发货 3:待收货(已发货) 5:成功 6:失败',
    all_count     int                                null comment '订单商品总数',
    pay_time      datetime                           null comment '付款时间',
    delivery_time datetime                           null comment '发货时间',
    finally_time  datetime                           null comment '完成时间',
    settled_time  datetime                           null comment '结算时间',
    cancel_time   datetime                           null comment '取消时间',
    is_payed      tinyint(1)                         null comment '是否已支付，1.已支付0.未支付',
    close_type    tinyint                            null comment '订单关闭原因 1-超时未支付 4-买家取消 15-已通过货到付款交易',
    delete_status tinyint  default 0                 null comment '用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除',
    version       int                                null comment '订单版本号，每处理一次订单，版本号+1',
    order_addr_id bigint                             null comment '用户订单地址id'
    )
    comment '订单信息';

create index idx_finally_time
    on order_info (finally_time);

create index idx_shop_id
    on order_info (shop_id);

create index idx_user_id
    on order_info (user_id);

create table if not exists order_item
(
    order_item_id    bigint unsigned auto_increment comment '订单项ID'
    primary key,
    create_time      datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    shop_id          bigint                                 not null comment '店铺id',
    order_id         bigint                                 not null comment '订单id',
    category_id      bigint                                 null comment '分类id',
    spu_id           bigint unsigned                        not null comment '产品ID',
    sku_id           bigint unsigned                        not null comment '产品SkuID',
    user_id          bigint                                 not null comment '用户Id',
    count            int          default 0                 null comment '购物车产品个数',
    spu_name         varchar(120) default ''                null comment '产品名称',
    sku_name         varchar(120)                           null comment 'sku名称',
    pic              varchar(255) default ''                not null comment '产品主图片路径',
    delivery_type    tinyint                                null comment '单个orderItem的配送类型3：无需快递',
    shop_cart_time   datetime                               null comment '加入购物车时间',
    price            bigint                                 not null comment '产品价格',
    spu_total_amount bigint                                 not null comment '商品总金额'
    )
    comment '订单项';

create index idx_order_id
    on order_item (order_id);

create index idx_shop_id
    on order_item (shop_id);

create index idx_sku_id
    on order_item (sku_id);

create index idx_spu_id
    on order_item (spu_id);

create index idx_user_id
    on order_item (user_id);

create table if not exists pay_info
(
    pay_id           bigint unsigned                    not null comment '支付单号'
    primary key,
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    user_id          bigint                             null comment '用户id',
    order_ids        varchar(255)                       null comment '本次支付关联的多个订单号',
    biz_pay_no       varchar(36)                        null comment '外部订单流水号',
    sys_type         tinyint                            null comment '系统类型 见SysTypeEnum',
    pay_status       tinyint                            null comment '支付状态',
    pay_amount       bigint                             null comment '支付金额',
    version          int                                null comment '版本号',
    callback_content varchar(4000)                      null comment '回调内容',
    callback_time    datetime                           null comment '回调时间',
    confirm_time     datetime                           null comment '确认时间'
    )
    comment '订单支付记录';

create index idx_biz_pay_no
    on pay_info (biz_pay_no);

create index idx_order_ids
    on pay_info (order_ids);

create index idx_user_id
    on pay_info (user_id);

create table if not exists role
(
    role_id        bigint unsigned auto_increment comment '角色id'
    primary key,
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    role_name      varchar(100)                       not null comment '角色名称',
    remark         varchar(100)                       null comment '备注',
    create_user_id bigint unsigned                    not null comment '创建者ID',
    biz_type       tinyint                            null comment '业务类型 1 店铺菜单 2平台菜单',
    tenant_id      bigint                             null comment '所属租户'
    )
    comment '角色';

create index idx_tenant_id
    on role (tenant_id);

create table if not exists role_menu
(
    id                 bigint unsigned auto_increment comment '关联id'
    primary key,
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    role_id            bigint unsigned                    not null comment '角色ID',
    menu_id            bigint unsigned                    null comment '菜单ID',
    menu_permission_id bigint unsigned                    null comment '菜单资源用户id',
    constraint uk_roleid_menu_permission_id
    unique (role_id, menu_id, menu_permission_id)
    )
    comment '角色与菜单对应关系';

create table if not exists shop_cart_item
(
    cart_item_id bigint unsigned auto_increment comment '主键'
    primary key,
    create_time  datetime        default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime        default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    shop_id      bigint                                    not null comment '店铺ID',
    spu_id       bigint unsigned default 0                 not null comment '产品ID',
    sku_id       bigint unsigned default 0                 not null comment 'SkuID',
    user_id      bigint unsigned                           not null comment '用户ID',
    count        int             default 0                 not null comment '购物车产品个数',
    price_fee    bigint unsigned                           not null comment '售价，加入购物车时的商品价格',
    is_checked   tinyint                                   null comment '是否已勾选',
    constraint uk_user_shop_sku
    unique (sku_id, user_id, shop_id)
    )
    comment '购物车' row_format = DYNAMIC;

create index idx_shop_id
    on shop_cart_item (shop_id);

create index idx_user_id
    on shop_cart_item (user_id);

create table if not exists shop_detail
(
    shop_id               bigint auto_increment comment '店铺id'
    primary key,
    create_time           datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time           datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    shop_name             varchar(50)                        not null comment '店铺名称',
    intro                 varchar(200)                       null comment '店铺简介',
    shop_logo             varchar(200)                       null comment '店铺logo(可修改)',
    mobile_background_pic varchar(200)                       null comment '店铺移动端背景图',
    shop_status           tinyint                            not null comment '店铺状态(-1:已删除 0: 停业中 1:营业中)',
    business_license      varchar(200)                       not null comment '营业执照',
    identity_card_front   varchar(200)                       null comment '身份证正面',
    identity_card_later   varchar(200)                       null comment '身份证反面',
    type                  tinyint                            null comment '店铺类型1自营店 2普通店'
    )
    comment '店铺详情';

create table if not exists shop_user
(
    shop_user_id bigint unsigned                    not null comment '商家用户id'
    primary key,
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    shop_id      bigint                             null comment '关联店铺id',
    nick_name    varchar(32)                        not null comment '昵称',
    avatar       varchar(255)                       null comment '头像',
    code         varchar(255)                       null comment '员工编号',
    phone_num    varchar(64)                        null comment '联系方式',
    has_account  tinyint                            null comment '是否已经设置账号 0:未设置 1:已设置'
    )
    comment '商家用户';

create index idx_shopid
    on shop_user (shop_id);

create table if not exists sku
(
    sku_id           bigint auto_increment comment '属性id'
    primary key,
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    spu_id           bigint                             not null comment 'SPU id',
    sku_name         varchar(255)                       null comment 'sku名称',
    attrs            varchar(255)                       null comment '多个销售属性值id逗号分隔',
    img_url          varchar(1000)                      null comment 'sku图片',
    price_fee        bigint   default 0                 not null comment '售价，整数方式保存',
    market_price_fee bigint   default 0                 not null comment '市场价，整数方式保存',
    party_code       varchar(100)                       null comment '商品编码',
    model_id         varchar(100)                       null comment '商品条形码',
    weight           decimal(15, 3)                     null comment '商品重量',
    volume           decimal(15, 3)                     null comment '商品体积',
    status           tinyint  default 0                 not null comment '状态 1:enable, 0:disable, -1:deleted'
    )
    comment 'sku信息' row_format = DYNAMIC;

create index idx_spuid
    on sku (spu_id);

create table if not exists sku_stock
(
    stock_id     bigint auto_increment comment '库存id'
    primary key,
    create_time  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    sku_id       bigint unsigned                        not null comment 'SKU ID',
    actual_stock int unsigned default 0                 not null comment '实际库存',
    lock_stock   int unsigned default 0                 not null comment '锁定库存',
    stock        int unsigned default 0                 not null comment '可售卖库存'
)
    comment '库存信息' row_format = DYNAMIC;

create index idx_skuid
    on sku_stock (sku_id);

create table if not exists sku_stock_lock
(
    id          bigint auto_increment comment 'id'
    primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    spu_id      bigint                             null comment '商品id',
    sku_id      bigint                             null comment 'sku id',
    order_id    bigint                             null comment '订单id',
    status      tinyint                            null comment '状态-1已解锁 0待确定 1已锁定',
    count       int                                null comment '锁定库存数量',
    constraint uni_spu_sku_order
    unique (spu_id, sku_id, order_id)
    )
    comment '库存锁定信息' row_format = DYNAMIC;

create index idx_order_id
    on sku_stock_lock (order_id);

create index idx_sku_id
    on sku_stock_lock (sku_id);

create table if not exists spu
(
    spu_id           bigint unsigned auto_increment comment 'spu id'
    primary key,
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    brand_id         bigint                             null comment '品牌ID',
    category_id      bigint                             not null comment '分类ID',
    shop_category_id bigint   default 0                 not null comment '店铺分类ID',
    shop_id          bigint                             not null comment '店铺id',
    name             varchar(255)                       null comment '商品名称',
    selling_point    varchar(255)                       null comment '卖点',
    main_img_url     varchar(255)                       null comment '商品介绍主图',
    img_urls         varchar(1000)                      null comment '商品图片 多个图片逗号分隔',
    video            varchar(150)                       null comment '商品视频',
    price_fee        bigint   default 0                 not null comment '售价，整数方式保存',
    market_price_fee bigint   default 0                 not null comment '市场价，整数方式保存',
    status           tinyint  default 0                 not null comment '状态 -1:删除, 0:下架, 1:上架',
    has_sku_img      tinyint  default 0                 not null comment 'sku是否含有图片 0无 1有',
    seq              smallint default 3                 not null comment '序号'
    )
    comment 'spu信息' row_format = DYNAMIC;

create index idx_brandid
    on spu (brand_id);

create index idx_catid
    on spu (category_id);

create index idx_shop_catid
    on spu (shop_category_id);

create index idx_shopid
    on spu (shop_id);

create table if not exists spu_attr_value
(
    spu_attr_value_id bigint auto_increment comment '商品属性值关联信息id'
    primary key,
    spu_id            bigint unsigned not null comment '商品id',
    attr_id           bigint unsigned not null comment '规格属性id',
    attr_name         varchar(255)    null comment '规格属性名称',
    attr_value_id     bigint          null comment '规格属性值id',
    attr_value_name   varchar(255)    null comment '规格属性值名称',
    attr_desc         varchar(255)    null comment '规格属性描述',
    constraint uni_spuid
    unique (spu_id, attr_id)
    )
    comment '商品规格属性关联信息' row_format = DYNAMIC;

create index idx_attrid
    on spu_attr_value (attr_id);

create table if not exists spu_detail
(
    spu_id      bigint                             not null comment '商品id'
    primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    detail      mediumtext                         null comment '商品详情'
)
    comment '商品详情信息' row_format = DYNAMIC;

create table if not exists spu_extension
(
    spu_extend_id bigint auto_increment comment '商品扩展信息表id'
    primary key,
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    spu_id        bigint unsigned                        not null comment '商品id',
    sale_num      int unsigned default 0                 not null comment '销量',
    actual_stock  int unsigned default 0                 not null comment '实际库存',
    lock_stock    int unsigned default 0                 not null comment '锁定库存',
    stock         int unsigned default 0                 not null comment '可售卖库存'
)
    comment '商品扩展信息表' row_format = DYNAMIC;

create index idx_spu
    on spu_extension (spu_id);

create table if not exists spu_sku_attr_value
(
    spu_sku_attr_id int unsigned auto_increment comment '商品sku销售属性关联信息id'
    primary key,
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    spu_id          bigint   default 0                 not null comment 'SPU ID',
    sku_id          bigint   default 0                 not null comment 'SKU ID',
    attr_id         int      default 0                 null comment '销售属性ID',
    attr_name       varchar(255)                       null comment '销售属性名称',
    attr_value_id   int      default 0                 null comment '销售属性值ID',
    attr_value_name varchar(255)                       null comment '销售属性值',
    status          tinyint  default 0                 not null comment '状态 1:enable, 0:disable, -1:deleted'
    )
    comment '商品sku销售属性关联信息' row_format = DYNAMIC;

create index idx_attrid
    on spu_sku_attr_value (attr_id);

create index idx_attrvalueid
    on spu_sku_attr_value (attr_value_id);

create index idx_skuid
    on spu_sku_attr_value (sku_id);

create index idx_spuid
    on spu_sku_attr_value (spu_id);

create table if not exists spu_tag
(
    id          bigint auto_increment comment '分组标签id'
    primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    title       varchar(36)                        null comment '分组标题',
    shop_id     bigint                             null comment '店铺Id',
    status      tinyint(1)                         null comment '状态(1为正常,-1为删除)',
    is_default  tinyint(1)                         null comment '默认类型(0:商家自定义,1:系统默认)',
    prod_count  bigint                             null comment '商品数量',
    style       int                                null comment '列表样式(0:一列一个,1:一列两个,2:一列三个)',
    seq         int                                null comment '排序',
    delete_time datetime                           null comment '删除时间'
    )
    comment '商品分组表' row_format = DYNAMIC;

create table if not exists spu_tag_reference
(
    reference_id bigint auto_increment comment '分组引用id'
    primary key,
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    shop_id      bigint                             null comment '店铺id',
    tag_id       bigint                             null comment '标签id',
    spu_id       bigint                             null comment '商品id',
    status       tinyint(1)                         null comment '状态(1:正常,-1:删除)',
    seq          int                                null comment '排序'
    )
    comment '商品分组标签关联信息' row_format = DYNAMIC;

create table if not exists sys_config
(
    id          bigint auto_increment
    primary key,
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    param_key   varchar(50)                        null comment 'key',
    param_value text                               null comment 'value',
    remark      varchar(500)                       null comment '备注',
    constraint `key`
    unique (param_key)
    )
    comment '系统配置信息表';

create table if not exists sys_user
(
    sys_user_id bigint unsigned                    not null comment '平台用户id'
    primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    nick_name   varchar(32)                        not null comment '昵称',
    avatar      varchar(255)                       not null comment '头像',
    code        varchar(255)                       null comment '员工编号',
    phone_num   varchar(64)                        null comment '联系方式',
    has_account tinyint                            null comment '是否已经设置账号'
    )
    comment '平台用户';

create table if not exists undo_log
(
    id            bigint auto_increment
    primary key,
    branch_id     bigint       not null,
    xid           varchar(100) not null,
    context       varchar(128) not null,
    rollback_info longblob     not null,
    log_status    int          not null,
    log_created   datetime     not null,
    log_modified  datetime     not null,
    constraint ux_undo_log
    unique (xid, branch_id)
    );

create table if not exists user
(
    user_id     bigint                             not null comment 'ID'
    primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '注册时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    nick_name   varchar(255)                       null comment '用户昵称',
    pic         varchar(255)                       null comment '头像图片路径',
    status      int      default 1                 not null comment '状态 1 正常 0 无效'
    )
    comment '用户表';

create table if not exists user_addr
(
    addr_id     bigint unsigned auto_increment comment 'ID'
    primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '建立时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    user_id     bigint                             not null comment '用户ID',
    mobile      varchar(20)                        null comment '手机',
    is_default  tinyint                            not null comment '是否默认地址 1是',
    consignee   varchar(50)                        null comment '收货人',
    province_id bigint                             null comment '省ID',
    province    varchar(100)                       null comment '省',
    city_id     bigint                             null comment '城市ID',
    city        varchar(20)                        null comment '城市',
    area_id     bigint                             null comment '区ID',
    area        varchar(20)                        null comment '区',
    post_code   varchar(15)                        null comment '邮编',
    addr        varchar(255)                       null comment '地址',
    lng         decimal(12, 6)                     null comment '经度',
    lat         decimal(12, 6)                     null comment '纬度'
    )
    comment '用户地址';

create index idx_user_id
    on user_addr (user_id);

create table if not exists user_role
(
    id          bigint unsigned auto_increment comment '关联id'
    primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    user_id     bigint unsigned                    not null comment '用户ID',
    role_id     bigint unsigned                    not null comment '角色ID',
    constraint uk_userid_roleid
    unique (user_id, role_id)
    )
    comment '用户与角色对应关系';

