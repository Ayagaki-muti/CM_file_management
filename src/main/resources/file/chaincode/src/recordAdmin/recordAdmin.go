package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

// 档案管理员表
type recordAdmin struct {
	// 档案管理员ID
	RecordAdminId string `json:"recordAdminId"`
	// 档案管理员密码
	RecordAdminPassword string `json:"recordAdminPassword"`
	// 档案管理员名字
	RecordAdminName string `json:"recordAdminName"`
	// 档案管理员部门ID
	DepartId string `json:"departId"`
	// 档案管理员等级
	RecordAdminLevel string `json:"recordAdminLevel"`
	// 档案管理员联系方式
	RecordAdminPhone string `json:"recordAdminPhone"`
}

func (t *recordAdmin) Init(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (t *recordAdmin) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	funcName, args := stub.GetFunctionAndParameters()
	if funcName == "save" {
		return t.saveBasic(stub, args)
	} else if funcName == "query" {
		return t.queryBasic(stub, args)
	} else {
		return shim.Error("no such function")
	}
}

func (t *recordAdmin) saveBasic(stub shim.ChaincodeStubInterface, args []string) pb.Response {
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

func (t *recordAdmin) queryBasic(stub shim.ChaincodeStubInterface, args []string) pb.Response {

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
	err := shim.Start(new(recordAdmin))
	if err != nil {
		fmt.Println("emr basicinfo chaincode start error")
	}
}
