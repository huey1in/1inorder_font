"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const common_assets = require("../../common/assets.js");
class OrderItem extends UTS.UTSType {
  static get$UTSMetadata$() {
    return {
      kind: 2,
      get fields() {
        return {
          id: { type: Number, optional: false },
          quantity: { type: Number, optional: false },
          price: { type: Number, optional: false },
          product_name: { type: String, optional: false },
          images: { type: "Unknown", optional: false }
        };
      },
      name: "OrderItem"
    };
  }
  constructor(options, metadata = OrderItem.get$UTSMetadata$(), isJSONParse = false) {
    super();
    this.__props__ = UTS.UTSType.initProps(options, metadata, isJSONParse);
    this.id = this.__props__.id;
    this.quantity = this.__props__.quantity;
    this.price = this.__props__.price;
    this.product_name = this.__props__.product_name;
    this.images = this.__props__.images;
    delete this.__props__;
  }
}
class Order extends UTS.UTSType {
  static get$UTSMetadata$() {
    return {
      kind: 2,
      get fields() {
        return {
          id: { type: Number, optional: false },
          order_number: { type: String, optional: false },
          status: { type: String, optional: false },
          total_amount: { type: Number, optional: false },
          items: { type: "Unknown", optional: false }
        };
      },
      name: "Order"
    };
  }
  constructor(options, metadata = Order.get$UTSMetadata$(), isJSONParse = false) {
    super();
    this.__props__ = UTS.UTSType.initProps(options, metadata, isJSONParse);
    this.id = this.__props__.id;
    this.order_number = this.__props__.order_number;
    this.status = this.__props__.status;
    this.total_amount = this.__props__.total_amount;
    this.items = this.__props__.items;
    delete this.__props__;
  }
}
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      statusTabs: [
        new UTSJSONObject({ label: "全部", value: "" }),
        new UTSJSONObject({ label: "已确认", value: "confirmed" }),
        new UTSJSONObject({ label: "制作中", value: "preparing" }),
        new UTSJSONObject({ label: "待取餐", value: "ready" }),
        new UTSJSONObject({ label: "配送中", value: "delivering" }),
        new UTSJSONObject({ label: "已完成", value: "delivered" }),
        new UTSJSONObject({ label: "已取消", value: "cancelled" })
      ],
      currentStatus: "",
      orders: [],
      loading: false,
      noMore: false,
      page: 1
    };
  },
  computed: {
    displayOrders() {
      if (this.currentStatus === "")
        return this.orders;
      if (this.currentStatus === "pending") {
        return this.orders.filter((o = null) => {
          return o.status === "pending" && o.payment_status !== "paid";
        });
      }
      return this.orders.filter((o = null) => {
        return o.status === this.currentStatus;
      });
    }
  },
  onShow() {
    const status = common_vendor.index.getStorageSync("orderStatus");
    if (status) {
      this.currentStatus = status;
      common_vendor.index.removeStorageSync("orderStatus");
      this.page = 1;
      this.orders = [];
      this.noMore = false;
    }
    this.fetchOrders();
  },
  methods: {
    getProductImage(item = null) {
      if (item.images && item.images.length > 0) {
        return item.images[0];
      }
      return DEFAULT_FOOD_IMAGE;
    },
    fetchOrders() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token) {
        return null;
      }
      this.loading = true;
      const requestData = new UTSJSONObject({
        page: this.page,
        limit: 10
      });
      if (this.currentStatus !== "") {
        requestData.status = this.currentStatus;
      }
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/orders/my`,
        method: "GET",
        header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
        data: requestData,
        success: (res = null) => {
          if (res.data.success && res.data.data.orders) {
            const newOrders = res.data.data.orders;
            if (this.page === 1) {
              this.orders = newOrders;
            } else {
              this.orders = [...this.orders, ...newOrders];
            }
            this.noMore = newOrders.length < 10;
          }
        },
        complete: () => {
          this.loading = false;
        }
      });
    },
    switchStatus(status) {
      this.currentStatus = status;
      this.page = 1;
      this.noMore = false;
      this.orders = [];
      this.fetchOrders();
    },
    loadMore() {
      if (this.loading || this.noMore)
        return null;
      this.page++;
      this.fetchOrders();
    },
    getStatusText(order = null) {
      const map = {
        "pending": "待确认",
        "confirmed": "已确认",
        "preparing": "制作中",
        "ready": "待取餐",
        "delivering": "配送中",
        "delivered": "已完成",
        "cancelled": "已取消"
      };
      return map[order === null || order === void 0 ? null : order.status] || (order === null || order === void 0 ? null : order.status) || "";
    },
    showDelete(order = null) {
      return (order === null || order === void 0 ? null : order.status) === "cancelled" || (order === null || order === void 0 ? null : order.status) === "delivered";
    },
    goDetail(id) {
      common_vendor.index.navigateTo({ url: `/pages/order/detail?id=${id}` });
    },
    cancelOrder(order) {
      common_vendor.index.showModal(new UTSJSONObject({
        title: "取消订单",
        content: "确定要取消该订单吗？",
        success: (res) => {
          if (res.confirm) {
            const token = common_vendor.index.getStorageSync("token");
            common_vendor.index.request({
              url: `${common_config.BASE_URL}/orders/${order.id}/cancel`,
              method: "POST",
              header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
              success: (res2 = null) => {
                if (res2.data.success) {
                  common_vendor.index.showToast({ title: "已取消", icon: "success" });
                  this.page = 1;
                  this.fetchOrders();
                }
              }
            });
          }
        }
      }));
    },
    payOrder(order) {
      common_vendor.index.showToast({ title: "支付功能开发中", icon: "none" });
    },
    reorder(order) {
      common_vendor.index.showToast({ title: "已加入购物车", icon: "success" });
      common_vendor.index.switchTab({ url: "/pages/cart/cart" });
    },
    deleteOrder(order) {
      common_vendor.index.showModal({
        title: "删除订单",
        content: "确定删除该订单吗？",
        success: (res) => {
          if (!res.confirm)
            return null;
          const token = common_vendor.index.getStorageSync("token");
          common_vendor.index.request({
            url: `${common_config.BASE_URL}/orders/${order.id}`,
            method: "DELETE",
            header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
            success: (resp = null) => {
              var _a, _b;
              if ((_a = resp.data) === null || _a === void 0 ? null : _a.success) {
                common_vendor.index.showToast({ title: "已删除", icon: "success" });
                this.page = 1;
                this.orders = [];
                this.noMore = false;
                this.fetchOrders();
              } else {
                common_vendor.index.showToast({ title: ((_b = resp.data) === null || _b === void 0 ? null : _b.message) || "删除失败", icon: "none" });
              }
            },
            fail: () => {
              common_vendor.index.showToast({ title: "网络错误", icon: "none" });
            }
          });
        }
      });
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: common_vendor.f($data.statusTabs, (tab, k0, i0) => {
      return {
        a: common_vendor.t(tab.label),
        b: tab.value,
        c: common_vendor.n($data.currentStatus === tab.value ? "active" : ""),
        d: common_vendor.o(($event) => $options.switchStatus(tab.value), tab.value)
      };
    }),
    b: common_vendor.f($options.displayOrders, (order, k0, i0) => {
      var _a;
      return common_vendor.e({
        a: common_vendor.t(order.order_number),
        b: common_vendor.t($options.getStatusText(order)),
        c: common_vendor.n(`status-${order.status}`),
        d: common_vendor.f(order.items, (item, k1, i1) => {
          return {
            a: $options.getProductImage(item),
            b: common_vendor.t(item.product_name || "商品"),
            c: common_vendor.t(item.quantity),
            d: common_vendor.t(item.price),
            e: item.id
          };
        }),
        e: common_vendor.t(((_a = order.items) == null ? void 0 : _a.length) || 0),
        f: common_vendor.t(order.total_amount),
        g: order.status === "pending" && order.payment_status !== "paid"
      }, order.status === "pending" && order.payment_status !== "paid" ? {
        h: common_vendor.o(($event) => $options.cancelOrder(order), order.id)
      } : {}, {
        i: order.status === "pending" && order.payment_status !== "paid"
      }, order.status === "pending" && order.payment_status !== "paid" ? {
        j: common_vendor.o(($event) => $options.payOrder(order), order.id)
      } : {}, {
        k: $options.showDelete(order)
      }, $options.showDelete(order) ? {
        l: common_vendor.o(($event) => $options.deleteOrder(order), order.id)
      } : {}, {
        m: order.id,
        n: common_vendor.o(($event) => $options.goDetail(order.id), order.id)
      });
    }),
    c: $options.displayOrders.length === 0 && !$data.loading
  }, $options.displayOrders.length === 0 && !$data.loading ? {
    d: common_assets._imports_0$3
  } : {}, {
    e: $data.loading
  }, $data.loading ? {} : {}, {
    f: !$data.loading && $data.noMore && $data.orders.length > 0
  }, !$data.loading && $data.noMore && $data.orders.length > 0 ? {} : {}, {
    g: common_vendor.o((...args) => $options.loadMore && $options.loadMore(...args)),
    h: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/order/list.js.map
