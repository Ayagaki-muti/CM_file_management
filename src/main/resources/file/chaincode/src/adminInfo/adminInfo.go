package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

// 管理员信息结构体
type adminInfo struct {
	// 管理员ID
	AdminId string `json:"adminId"`
	// 管理员密码
	AdminPassword string `json:"adminPassword"`
	// 管理员名字
	AdminName string `json:"adminName"`
	// 管理员性别
	AdminSex string `json:"adminSex"`
	// 管理员地址
	AdminAddress string `json:"adminAddress"`
	// 管理员电话
	AdminPhone string `json:"adminPhone"`
	// 管理员等级
	AdminLevel string `json:"adminLevel"`
}

func (t *adminInfo) Init(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (t *adminInfo) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	funcName, args := stub.GetFunctionAndParameters()
	if funcName == "save" {
		return t.saveBasic(stub, args)
	} else if funcName == "query" {
		return t.query(stub, args)
	} else {
		return shim.Error("no such function")
	}
}

func (t *adminInfo) saveBasic(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 2 {
		return shim.Error("except two args")
	} else {
		err := stub.PutState(args[0], []byte(args[1]))
		if err != nil {
			return shim.Error(err.Error())
		}
		return shim.Success(nil)
	}
}

func (t *adminInfo) query(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 1 {//判断参数个数
		return shim.Error("except one arg")
	} else {
		value, err := stub.GetState(args[0])
		if err != nil {
			shim.Error("no data found")
		}
		return shim.Success(value)
	}
}

func main() {
	err := shim.Start(new(adminInfo))
	if err != nil {
		fmt.Println("emr adminInfo chaincode start error")
	}
}
