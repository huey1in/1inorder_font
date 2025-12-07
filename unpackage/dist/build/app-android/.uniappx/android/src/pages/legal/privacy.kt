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
open class GenPagesLegalPrivacy : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("text", _uM("class" to "title"), "隐私政策"),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "sub-title"), "1. 信息收集"),
                _cE("text", _uM("class" to "paragraph"), "为提供下单、配送、自取等服务，我们可能收集手机号、地址、订单记录等必要信息。")
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "sub-title"), "2. 信息使用"),
                _cE("text", _uM("class" to "paragraph"), "收集的信息仅用于订单处理、客服沟通、服务优化，不会用于未授权的其他用途。")
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "sub-title"), "3. 信息存储与保护"),
                _cE("text", _uM("class" to "paragraph"), "我们采取合理的安全措施保护您的数据安全，未经授权不会向第三方披露，法律法规另有规定的除外。")
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "sub-title"), "4. 用户权利"),
                _cE("text", _uM("class" to "paragraph"), "您可以联系平台查询、更新或删除个人信息，也可注销账户后停止继续使用相关服务。")
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
