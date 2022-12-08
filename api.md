#Fabric接口文档

##一、FabricSDK("adminInfo")——系统管理员信息
###1.queryChaincode(initArgsQuery)——查询系统管理员信息
输入：管理员id   
输出：[{"adminId":"2","adminPassword":"111111","adminName":"王","adminSex":"男","adminAddress":"北京市","adminPhone":"15611663535","adminLevel":"级别1"}]

###2.invoke(initArgsInvoke)——执行智能合约完成注册系统管理员
输入：String[] initArgsInvoke =
{adminInfo.getAdminId(),
"{\"adminId\":\"" + adminInfo.getAdminId() + "\"," +
"\"adminPassword\":\"" + adminInfo.getAdminPassword() + "\"," +
"\"adminName\":\"" + adminInfo.getAdminName() + "\"," +
"\"adminSex\":\"" + adminInfo.getAdminSex() + "\"," +
"\"adminAddress\":\"" + adminInfo.getAdminAddress() + "\"," +
"\"adminPhone\":\"" + adminInfo.getAdminPhone() + "\"," +
"\"adminLevel\":\"" + adminInfo.getAdminLevel() + "\"}"
};  
输出：成功——true；失败——false  

##二、FabricSDK("departInfo")——部门信息
###1.queryChaincode(initArgsQuery)——查询部门信息
输入：initArgsQuery = {departId} 部门ID  
输出：[{"departId":"2","departName":"3级档案馆","departSuperior":"2级档案馆"}]

###2.invoke(initArgsInvoke)——执行智能合约完成添加部门信息
输入：String[] initArgsInvoke =
{departInfo.getDepartId(),
"{\"departId\":\"" + departInfo.getDepartId() + "\"," +
"\"departName\":\"" + departInfo.getDepartName() + "\"," +
"\"departSuperior\":\"" + departInfo.getDepartSuperior() + "\"}"
};  
输出：成功——true；失败——false 

##三、FabricSDK("recordAdmin")——档案管理员信息
###1.queryChaincode(initArgsQuery)——查询档案管理员信息
输入：initArgsQuery = {recordAdminId}; 档案管理员id  
输出：[{"recordAdminId":"2","recordAdminPassword":"666666","recordAdminName":"王二","departId":"1","recordAdminLevel":"等级1","recordAdminPhone":"18181818181"}]


###2.invoke(initArgsInvoke)——执行智能合约完成添加档案管理员
输入：String[] initArgsInvoke =
{recordAdmin.getRecordAdminId(),
"{\"recordAdminId\":\"" + recordAdmin.getRecordAdminId() + "\"," +
"\"recordAdminPassword\":\"" + recordAdmin.getRecordAdminPassword() + "\"," +
"\"recordAdminName\":\"" + recordAdmin.getRecordAdminName() + "\"," +
"\"departId\":\"" + recordAdmin.getDepartId() + "\"," +
"\"recordAdminLevel\":\"" + recordAdmin.getRecordAdminLevel() + "\"," +
"\"recordAdminPhone\":\"" + recordAdmin.getRecordAdminPhone() + "\"}"
};   
输出：成功——true；失败——false 

##四、FabricSDK("recordAuth")——档案授权信息
###1.queryChaincode(initArgsQuery)——查询最新档案授权信息
输入：initArgsQuery = {recordAuthId} 档案授权ID  
输出：[{"recordAuthId":"1","recordAdminId":"2","recordUserId":"1","recordId":"1","recordAuthTime":"2022-12-02 16:11","recordAuthTips":"授权2.0","recordAuthCutOffTime":"2022-12-03 19:10"}]

###2.queryAllChaincode(initArgsQuery)——查询所有档案授权信息
输入：initArgsQuery = {recordAuthId} 档案授权ID  
输出：[{"recordInfos":[{"recordAuthId":"1","recordAdminId":"1","recordUserId":"1","recordId":"1","recordAuthTime":"2022-12-02 16:11","recordAuthCutOffTime":"2022-12-03 19:10","recordAuthTips":"授权"},{"recordAuthId":"1","recordAdminId":"2","recordUserId":"1","recordId":"1","recordAuthTime":"2022-12-02 16:11","recordAuthCutOffTime":"2022-12-03 19:10","recordAuthTips":"授权2.0"}]}]

###3.invoke(initArgsInvoke)——执行智能合约完成添加档案授权
输入：String[] initArgsInvoke =
{recordAuth.getRecordAuthId(),
"{\"recordAuthId\":\"" + recordAuth.getRecordAuthId() + "\"," +
"\"recordAdminId\":\"" + recordAuth.getRecordAdminId() + "\"," +
"\"recordUserId\":\"" + recordAuth.getRecordUserId() + "\"," +
"\"recordId\":\"" + recordAuth.getRecordId() + "\"," +
"\"recordAuthTime\":\"" + recordAuth.getRecordAuthTime() + "\"," +
"\"recordAuthTips\":\"" + recordAuth.getRecordAuthTips() + "\"," +
"\"recordAuthCutOffTime\":\"" + recordAuth.getRecordAuthCutOffTime() + "\"}"
};  
输出：成功——true；失败——false 

##五、FabricSDK("recordInfo")——档案内容信息
###1.queryChaincode(initArgsQuery)——查询最新档案信息
输入：initArgsQuery = {recordId} 档案ID  
输出：[{"recordId":"1","recordAdminId":"1","recordName":"档案2.0","recordVersion":"2.0","recordTime":"2022-12-02 16:02","fileHash":"3d2172418ce305c7d16d4b05597c6a59.0","recordDescribe":"档案2.0号"}]

###2.queryAllChaincode(initArgsQuery)——查询所有档案信息
输入：initArgsQuery = {recordId}; 档案ID  
输出：[{"recordInfos":[{"recordId":"1","recordAdminId":"0","recordName":"档案1.0","recordVersion":"1.0","recordTime":"2022-12-02 16:02","fileHash":"7fa8282ad93047a4d6fe6111c93b308a.0","recordDescribe":"档案1.0号"},{"recordId":"1","recordAdminId":"1","recordName":"档案2.0","recordVersion":"2.0","recordTime":"2022-12-02 16:02","fileHash":"3d2172418ce305c7d16d4b05597c6a59.0","recordDescribe":"档案2.0号"}]}]

###3.invoke(initArgsInvoke)——执行智能合约完成添加档信息
输入：String[] initArgsInvoke =
{recordInfo.getRecordId(),
"{\"recordId\":\"" + recordInfo.getRecordId() + "\"," +
"\"recordAdminId\":\"" + recordInfo.getRecordAdminId() + "\"," +
"\"recordName\":\"" + recordInfo.getRecordName() + "\"," +
"\"recordVersion\":\"" + recordInfo.getRecordVersion() + "\"," +
"\"recordTime\":\"" + recordInfo.getRecordTime() + "\"," +
"\"fileHash\":\"" + recordInfo.getFileHash() + "\"," +
"\"recordDescribe\":\"" + recordInfo.getRecordDescribe() + "\"}"
};   
输出：成功——true；失败——false

##六、FabricSDK("userInfo")——普通用户信息
###1.queryChaincode(initArgsQuery)——查询用户信息
输入：initArgsQuery = {userId} 用户ID  
输出：[{"userId":"2","userPassword":"1515151","userName":"王法","userSex":"男","userAddress":"北京市","userPhone":"14315214211"}]

###2.invoke(initArgsInvoke)——执行智能合约完成添用户信息
输入：String[] initArgsInvoke =
{userInfo.getUserId(),
"{\"userId\":\"" + userInfo.getUserId() + "\"," +
"\"userPassword\":\"" + userInfo.getUserPassword() + "\"," +
"\"userName\":\"" + userInfo.getUserName() + "\"," +
"\"userSex\":\"" + userInfo.getUserSex() + "\"," +
"\"userAddress\":\"" + userInfo.getUserAddress() + "\"," +
"\"userPhone\":\"" + userInfo.getUserPhone() + "\"}"
};  
输出：成功——true；失败——false

##七、FabricSDK("keyValue")——各项 ID 的简表信息
###1.queryAllChaincode(initArgsQuery)——查询所有 ID 的简表信息
输入：initArgsQuery = {key}; key为属于哪个表的简表，如"key":"recordAuth"是档案授权ID的简表     
输出：[{"recordInfos":[{"key":"recordAuth","valueA":"1","valueB":"1"},{"key":"recordAuth","valueA":"1","valueB":"1"}]}]

###2.invoke(initArgsInvoke)——执行智能合约完成添加各项 ID 的简表信息
输入：String[] initArgsInvoke =
{keyValue.getKey(),
"{\"Key\":\"" + keyValue.getKey() + "\"," +
"\"ValueA\":\"" + keyValue.getValueA() + "\"," +
"\"ValueB\":\"" + keyValue.getValueB() + "\"}"
};   
输出：成功——true；失败——false