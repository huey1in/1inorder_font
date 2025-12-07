@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNI9239644
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uniapp.framework.*
import io.dcloud.uniapp.runtime.*
import io.dcloud.uniapp.vue.*
import io.dcloud.uniapp.vue.shared.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
open class GenPagesLegalAgreement : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("text", _uM("class" to "title"), "用户协议"),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "sub-title"), "1. 总则"),
                _cE("text", _uM("class" to "paragraph"), "本协议适用于本小程序的使用与下单服务，请在使用前仔细阅读。")
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "sub-title"), "2. 账户与安全"),
                _cE("text", _uM("class" to "paragraph"), "请确保账号与密码安全，因保管不当导致的损失由用户自行承担。")
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "sub-title"), "3. 订单与支付"),
                _cE("text", _uM("class" to "paragraph"), "下单即视为认可商品与价格，支付成功后订单将进入制作流程，具体以平台提示为准。")
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "sub-title"), "4. 配送/自取"),
                _cE("text", _uM("class" to "paragraph"), "配送地址或自取信息请确认无误，因信息错误产生的损失由用户承担。")
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "sub-title"), "5. 其他"),
                _cE("text", _uM("class" to "paragraph"), "本协议未尽事宜，以平台实际规则及提示为准。")
            ))
        ))
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ), _uA(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("container" to _pS(_uM("backgroundImage" to "none", "backgroundColor" to "#FFF5F8", "paddingTop" to "32rpx", "paddingRight" to "32rpx", "paddingBottom" to "32rpx", "paddingLeft" to "32rpx", "boxSizing" to "border-box", "color" to "#5D4E6D")), "title" to _pS(_uM("fontSize" to "36rpx", "color" to "#FF69B4", "marginBottom" to "24rpx")), "section" to _pS(_uM("marginBottom" to "28rpx")), "sub-title" to _pS(_uM("fontSize" to "30rpx", "fontWeight" to "700", "marginBottom" to "10rpx")), "paragraph" to _pS(_uM("fontSize" to "28rpx", "lineHeight" to 1.6, "color" to "#6b6575")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
