package com.cj.common.startup;

import com.cj.common.service.AuthRoleModularService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.regex.Pattern;

/**
 * 开机启动加载角色-权限列表
 */
@Component
@Order(2147483647)
public class LoadPowerList implements CommandLineRunner {

    @Resource
    private AuthRoleModularService authRoleModularService;
    @Override
    public void run(String... strings) throws Exception {
//        System.out.println("=================初始化角色-权限数据====================");
        authRoleModularService.findRoleModular();

    }
//
//    public static void main(String[] args) {
//        String[] whiteList = new String[]{
//                "/api/v1/admin/*"
//        };
//        String s = "/api/v1/admin/logi/1";
//        boolean r =  wildcardEquals(whiteList,s);
//        System.out.println(r);
//    }
//
//    /**
//     * 通配符模式
//     * @param whitePath - 白名单地址
//     * @param reqPath - 请求地址
//     * @return
//     */
//    private static boolean wildcardEquals(String[] whitePath, String reqPath) {
//        boolean b = false;
//        for (String url : whitePath){
//            String regPath = getRegPath(url);
//            b = Pattern.compile(regPath).matcher(reqPath).matches();
////            b = Pattern.matches(regPath,reqPath);  上面一句效率更高，此句不可重用已编译部分
//
//            if (b == true){
//                break;
//            }
//        }
//        return b;
//    }
//    /**
//     * 将通配符表达式转化为正则表达式
//     * @param path
//     * @return
//     */
//    private static String getRegPath(String path) {
//        char[] chars = path.toCharArray();
//        int len = chars.length;
//        StringBuilder sb = new StringBuilder();
//        boolean preX = false;
//        for(int i=0;i<len;i++){
//            if (chars[i] == '*'){//遇到*字符
//                if (preX){//如果是第二次遇到*，则将**替换成.*
//                    sb.append(".*");
//                    preX = false;
//                }else if(i+1 == len){//如果是遇到单星，且单星是最后一个字符，则直接将*转成[^/]*
//                    sb.append("[^/]*");
//                }else{//否则单星后面还有字符，则不做任何动作，下一把再做动作
//                    preX = true;
//                    continue;
//                }
//            }else{//遇到非*字符
//                if (preX){//如果上一把是*，则先把上一把的*对应的[^/]*添进来
//                    sb.append("[^/]*");
//                    preX = false;
//                }
//                if (chars[i] == '?'){//接着判断当前字符是不是?，是的话替换成.
//                    sb.append('.');
//                }else{//不是?的话，则就是普通字符，直接添进来
//                    sb.append(chars[i]);
//                }
//            }
//        }
//        return sb.toString();
//    }
}
