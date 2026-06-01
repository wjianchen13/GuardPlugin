package com.yumi.plugin.entension

/**
 *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 *           ░     ░ ░      ░  ░
 * @author : Leo
 * @date : 2022/12/16 19:08
 * @desc :
 * @since : xinxiniscool@gmail.com
 */
open class ConfigExtension {

    var classPrefixName: Array<String> = arrayOf("")

    /**
     * 各模块独立前缀配置，key 为模块名，value 为前缀数组
     * 配置了此项的模块使用对应前缀，未配置的模块回退到 classPrefixName
     * 示例：moduleClassPrefixName = ["lib_a": ["LeoA", "LeoB"], "lib_b": ["LeoC"]]
     */
    var moduleClassPrefixName: Map<String, List<String>> = emptyMap()

    /**
     * 全局中缀，插入到类名 length/2 位置；空则不插入中缀
     * 示例：classMiddleName = ["Mid1", "Mid2"]
     */
    var classMiddleName: Array<String> = arrayOf()

    /**
     * 各模块独立中缀配置，未配置的模块回退到 classMiddleName
     * 示例：moduleClassMiddleName = ["hyk_app": ["Mid1", "Mid2"]]
     */
    var moduleClassMiddleName: Map<String, List<String>> = emptyMap()

    /**
     * 全局后缀，追加到类名末尾；空则不追加后缀
     * 示例：classAfterName = ["Suf1", "Suf2"]
     */
    var classAfterName: Array<String> = arrayOf()

    /**
     * 各模块独立后缀配置，未配置的模块回退到 classAfterName
     * 示例：moduleClassAfterName = ["hyk_app": ["Suf1", "Suf2"]]
     */
    var moduleClassAfterName: Map<String, List<String>> = emptyMap()

    var dirPrefixName: Array<String> = arrayOf("")

    var resPrefixName: Array<String> = arrayOf("")

    var filterSuffixFiles: Array<String> = arrayOf("")

    /**
     * 全局过滤类名列表：列表内的类名不参与重命名（匹配文件名去掉扩展名后的名称）
     * 示例：filterClassNames = ["MainActivity", "BaseActivity"]
     */
    var filterClassNames: Array<String> = arrayOf()

    /**
     * 各模块独立过滤列表，与 filterClassNames 取并集一起生效
     * 示例：moduleFilterClassNames = ["hyk_app": ["MainActivity", "SplashActivity"]]
     */
    var moduleFilterClassNames: Map<String, List<String>> = emptyMap()

    var changeResDir: Array<String>? = null

    var junkPackage = "com.leos.superplugin"

    var junkResPackage = "com.leos.superplugin"

    var activityClassMethodCount = 0

    var activityClassCount = 0

    var normalClassCount = 0

    var normalClassMethodCount = 0

    var layoutClassCount = 0

    var layoutClassMethodCount = 0

    var drawableClassCount = 0

    var colorCount = 0

    var stringsCount = 0

    var colorPrefixName: Array<String> = arrayOf("")

    var stringsPrefixName: Array<String> = arrayOf("")

}