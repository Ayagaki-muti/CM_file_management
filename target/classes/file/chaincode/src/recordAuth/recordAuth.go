package main

import (
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

// 档案授权结构体
type recordAuth struct {
	// 档案授权信息ID
	RecordAuthId string `json:"recordAuthId"`
	// 档案授权人ID
	RecordAdminId string `json:"recordAdminId"`
	// 所属人ID
	RecordUserId string `json:"recordUserId"`
	// 档案编号
	RecordId string `json:"recordId"`
	// 档案授权时间
	RecordAuthTime string `json:"recordAuthTime"`
	// 授权截止时间
	RecordAuthCutOffTime string `json:"recordAuthCutOffTime"`
	// 授权备注
	RecordAuthTips string `json:"recordAuthTips"`
}

type resultData struct {
	RecordInfos []recordAuth `json:"recordInfos"`
}

func (r *recordAuth) Init(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (r *recordAuth) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	funcName, args := stub.GetFunctionAndParameters()
	if funcName == "save" {
		return r.saveRecord(stub, args)
	} else if funcName == "query" {
		return r.queryRecord(stub, args)
	} else if funcName == "queryHistory" {
		return r.queryHistoryRecord(stub, args)
	} else {
		return shim.Error("no such function")
	}
}

func (r *recordAuth) saveRecord(stub shim.ChaincodeStubInterface, args []string) pb.Response {
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

func (r *recordAuth) queryRecord(stub shim.ChaincodeStubInterface, args []string) pb.Response {
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

func (r *recordAuth) queryHistoryRecord(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 1 {
		return shim.Error("except one arg")
	} else {
		//queryParam :="{\"selector\":{\"identity\":\""+id+"\"}}"
		recordinfos := []recordAuth{}
		it, err := stub.GetHistoryForKey(args[0])
		if err != nil {
			return shim.Error("no data found")
		} else {
			fmt.Println("is data exits?", it.HasNext())
			for it.HasNext() {
				keym, err := it.Next()
				if err != nil {
					return shim.Error("data get error")
				}
				value := keym.Value
				fmt.Println("get value is", string(value))
				recordinfo := new(recordAuth)
				json.Unmarshal(value, &recordinfo)
				fmt.Println("recordinfo is ", recordinfo)
				recordinfos = append(recordinfos, *recordinfo)
				fmt.Println("recordinfos is ", recordinfos)
			}
			resultdata := new(resultData)
			resultdata.RecordInfos = recordinfos
			fmt.Println("resultdata is ", resultdata)
			value, err := json.Marshal(resultdata)
			if err != nil {
				shim.Error(err.Error())
			}
			return shim.Success(value)
		}
	}
}

func main() {
	err := shim.Start(new(recordAuth))
	if err != nil {
		fmt.Println("emr recordAuth chaincode start error")
	}
}
