package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

// 所属人信息结构体
type userInfo struct {
	// 所属人ID
	UserId string `json:"userId"`
	// 所属人密码
	UserPassword string `json:"userPassword"`
	// 所属人名字
	UserName string `json:"userName"`
	// 所属人性别
	UserSex string `json:"userSex"`
	// 所属人地址
	UserAddress string `json:"userAddress"`
	// 所属人电话
	UserPhone string `json:"userPhone"`
}

func (t *userInfo) Init(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (t *userInfo) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	funcName, args := stub.GetFunctionAndParameters()
	if funcName == "save" {
		return t.saveBasic(stub, args)
	} else if funcName == "query" {
		return t.query(stub, args)
	} else {
		return shim.Error("no such function")
	}
}

func (t *userInfo) saveBasic(stub shim.ChaincodeStubInterface, args []string) pb.Response {
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

func (t *userInfo) query(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 1 {
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
	err := shim.Start(new(userInfo))
	if err != nil {
		fmt.Println("emr userInfo chaincode start error")
	}
}
