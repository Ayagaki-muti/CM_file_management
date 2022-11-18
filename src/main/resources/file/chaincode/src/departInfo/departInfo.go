package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

// 管理员部门信息结构体
type departInfo struct {
	// 管理员部门ID
	DepartId string `json:"departId"`
	// 管理员部门名称
	DepartName string `json:"departName"`
	// 管理员部门上级部门
	DepartSuperior string `json:"departSuperior"`
}

func (t *departInfo) Init(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (t *departInfo) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	funcName, args := stub.GetFunctionAndParameters()
	if funcName == "save" {
		return t.saveBasic(stub, args)
	} else if funcName == "query" {
		return t.query(stub, args)
	} else {
		return shim.Error("no such function")
	}
}

func (t *departInfo) saveBasic(stub shim.ChaincodeStubInterface, args []string) pb.Response {
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

func (t *departInfo) query(stub shim.ChaincodeStubInterface, args []string) pb.Response {
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
	err := shim.Start(new(departInfo))
	if err != nil {
		fmt.Println("emr departInfo chaincode start error")
	}
}
