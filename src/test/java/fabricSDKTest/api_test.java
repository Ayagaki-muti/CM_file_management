package fabricSDKTest;

import com.springboot.utils.fabricSDK.FabricSDK;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class api_test {

    @Test
    public void test1() {

        System.out.println("------------------------------测试----------------------------------------");
        //测试安装合约
        String a = (String) FabricSDK.installChaincode("1.0.0", "src/main/resources/file/chaincode", "keyValue");
        System.out.print("end"+a);

        //测试实例化合约
        String b = (String) FabricSDK.instantiated("keyValue", "1.0.0");
        System.out.print("end"+b);
        //测试升级合约
        String c = (String) FabricSDK.upgradeChaincode("adminInfo", "1.0.0");
        System.out.print(c);
        //测试invoke
        FabricSDK fabricSDK = new FabricSDK("adminInfo");
        String[] initArgsInvoke =
                {"1",
                        "{\"adminId\":\"" + "112" + "\"," +
                                "\"adminPassword\":\"" + "123456" + "\"," +
                                "\"adminName\":\"" + "wei" + "\"," +
                                "\"adminSex\":\"" + "男" + "\"," +
                                "\"adminAddress\":\"" + "gogo" + "\"," +
                                "\"adminPhone\":\"" + "13456789" + "\"," +
                                "\"adminLevel\":\"" + "A" + "\"}"
                };
        fabricSDK.invoke(initArgsInvoke);
        //测试查询合约
        String[] initArgsQuery = {"112"};
        fabricSDK.queryChaincode(initArgsQuery);



    }

}



   /* @Test
    public void tes2(){
        String[] initArgsInvoke =
                {"1",
                        "{\"adminId\":\"" + "1" + "\"," +
                                "\"adminPassword\":\"" + "123456" + "\"," +
                                "\"adminName\":\"" + "wei" + "\"," +
                                "\"adminSex\":\"" + "男" + "\"," +
                                "\"adminAddress\":\"" + "gogo" + "\"," +
                                "\"adminPhone\":\"" + "13456789" + "\"," +
                                "\"adminLevel\":\"" + "A" + "\"}"
                };
        for(int i=0;i<initArgsInvoke.length;i++){
            System.out.print(initArgsInvoke[i]);
        }



    }
}
*/