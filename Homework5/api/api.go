package api

import (
	"Homework5/sv"
	"Homework5/utils"
	"errors"
	"net/http"

	"github.com/gin-gonic/gin"
)

func RegisterHandler(c *gin.Context) {
	var RegisterRequest struct {
		Account  int    `json:"account"`
		Password string `json:"password"`
		Name     string `json:"name"`
		Grade    int    `json:"grade"`
	}
	err := c.ShouldBind(&RegisterRequest)
	if err != nil {
		c.JSON(400, gin.H{"err": err.Error()})
		return
	}
	Err := sv.Register(RegisterRequest.Password, RegisterRequest.Name, RegisterRequest.Grade, RegisterRequest.Account)
	if !errors.Is(Err, utils.Ok) {
		c.JSON(400, utils.FinalResponse{Status: "400", Info: "somethings wrong...", Data: Err})
		return
	}
}

func LogonHandler(c *gin.Context) {
	var loginRequest struct {
		Account  int    `json:"account"`
		Password string `json:"password"`
	}
	//把信息注入一下
	err := c.ShouldBindJSON(&loginRequest)
	//解析失败，，
	if err != nil {
		c.JSON(400, gin.H{"error": err.Error()})
		return
	}
	//验证账号密码,返回对应的ID和role
	ID, role, OK := sv.Logon(loginRequest.Password, loginRequest.Account)
	//验证出错，，，
	if !errors.Is(OK, utils.Ok) {
		c.JSON(400, gin.H{"error": utils.FinalResponse{Status: "400", Info: "something wrong...", Data: OK}})
		return
	}
	//验证通过，生成token
	AccessToken, RefreshToken, err := utils.CreateToken(ID, role)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "create token failed"})
	}
	c.JSON(200, gin.H{
		"message":      "login successful",
		"AccessToken":  AccessToken,
		"RefreshToken": RefreshToken,
		"Type":         "bearer",
	})
}

func CheckCourseHandler(c *gin.Context) {
	A := sv.CheckCourse()
	for A1 := range A {
		err := c.ShouldBind(&A[A1])
		c.String(200, "\n")
		c.JSON(200, gin.H{"msg": err, "data": A[A1]})
	}
}

func SelectCourseHandler(c *gin.Context) {
	ID1, err := c.Get("userID")
	if !err {
		c.JSON(400, gin.H{"err": err})
		return
	}
	ID := ID1.(int)
	courseName := c.Param("cour")
	Err := sv.SelectCourse(courseName, ID)
	if !errors.Is(Err, utils.Ok) {
		c.JSON(400, utils.FinalResponse{Status: "400", Info: "something wrong...", Data: Err})
		return
	}
}
func QuitCourseHandler(c *gin.Context) {
	ID1, err := c.Get("userID")
	if !err {
		c.JSON(400, gin.H{"err": err})
		return
	}
	ID := ID1.(int)
	courseName := c.Param("cour")
	Err := sv.QuitCourse(courseName, ID)
	if !errors.Is(Err, utils.Ok) {
		c.JSON(400, utils.FinalResponse{Status: "400", Info: "something wrong...", Data: Err})
		return
	}
}
func CheckSelectedCourseHandler(c *gin.Context) {
	ID1, err := c.Get("userID")
	if !err {
		c.JSON(400, gin.H{"err": err})
		return
	}
	ID := ID1.(int)
	A, Err := sv.CheckSelectedCourse(ID)
	if !errors.Is(Err, utils.Ok) {
		c.JSON(400, utils.FinalResponse{Status: "400", Info: "something wrong..."})
		return
	}
	c.JSON(200, gin.H{"message": "success", "data": A})
}
func CreateCourseHandler(c *gin.Context) {
	role, err := c.Get("userRole")
	if !err {
		c.JSON(400, gin.H{"err": err})
		return
	}
	if role.(string) != "admin" {
		c.JSON(400, gin.H{"err": "not admin"})
		return
	}
	courseName := c.Param("cour")
	Err := sv.CreateCourse(courseName)
	if !errors.Is(Err, utils.Ok) {
		c.JSON(400, utils.FinalResponse{Status: "400", Info: "something wrong...", Data: Err})
	}
}
