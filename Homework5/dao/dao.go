package dao

import (
	"Homework5/model"
	"Homework5/utils"
	"errors"
	"fmt"
	"log"
	"strings"

	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

var (
	DB *gorm.DB
)

func Setup() {
	host := "127.0.0.1"
	port := "3306"
	username := "root"
	password := "Aa3318752853"
	dbname := "test"
	dburl := username + ":" + password + "@(" + host + ":" + port + ")/" + dbname + "?charset=utf8&parseTime=True&loc=Local"
	db, err := gorm.Open(mysql.Open(dburl), &gorm.Config{})
	if err != nil {
		fmt.Println(dburl)
		log.Println("连接失败，，，", err)
		panic(err)
	}
	DB = db
	err1 := DB.AutoMigrate(&model.Student{})
	if err1 != nil {
		panic(err1)
	}
	err2 := DB.AutoMigrate(&model.Course{})
	if err2 != nil {
		panic(err2)
	}
}

func Register(pass string, name string, grade int, acc int) utils.Response {
	//检索有无重复账号
	var A []model.Student
	DB.Find(&A)
	//遍历A，获取其所有关于账号的信息。
	for ID := range A {
		if A[ID].Account == acc {
			//你妈逼，账号重复！
			return utils.RepeatAccount
		}
	}
	//如果账号不重复，正常执行操作。
	result := DB.Create(&model.Student{Name: name, Grade: grade, Account: acc, Password: pass, Role: "user"})
	//账号创建失败，内部错误。
	if result.Error != nil {
		return utils.InternalError(result.Error)
	}
	return utils.Ok
}
func Logon(pass string, acc int) (uint64, string, utils.Response) {
	var A model.Student
	//看看能不能找到这么个账号
	err := DB.First(&A, "account = ?", acc)
	if err.Error != nil {
		//呱，找不到口牙！
		return 0, "", utils.Response{Status: "40000", Info: "can not find account"}
	}
	//确认密码，，，
	if A.Password == pass {
		return uint64(A.Id), A.Role, utils.Ok
	} else {
		//密码错误，，，
		return 0, "", utils.WrongPWd
	}
}
func CreateCourse(name string) utils.Response {
	var A model.Course
	//判断下有没有重复
	err := DB.First(&A, "name = ?", name)
	if err.Error == nil {
		//真重复了，，，
		return utils.RepeatCourse
	}
	//一切正常这一块
	Err := DB.Create(&model.Course{Name: name, Amount: 0})
	//创建失败，内部错误
	if Err.Error != nil {
		return utils.InternalError(Err.Error)
	}
	return utils.Ok
}
func CheckCourse() []model.Course {
	var A []model.Course
	err := DB.Find(&A)
	if err.Error != nil {
		fmt.Println(err)
		return nil
	}
	return A
}
func CheckSelectedCourse(ID int) (model.Student, utils.Response) {
	var A model.Student
	err := DB.First(&A, ID)
	if err.Error != nil {
		//找不到对应的值，，
		return A, utils.WrongName
	}
	return A, utils.Ok
}
func judge(course string, ID int) (model.Course, model.Student, utils.Response) {
	//先拿到课程和学生的信息
	var A model.Course
	var B model.Student
	//判断课程，学生是否存在
	err := DB.First(&A, "name=?", course)
	if err.Error != nil {
		return model.Course{}, model.Student{}, utils.Response{Status: "40003", Info: "course not found!"}
	}
	err = DB.First(&B, ID)
	if err.Error != nil {
		return model.Course{}, model.Student{}, utils.Response{Status: "40001", Info: "students not found!"}
	}

	//都没问题
	return A, B, utils.Ok
}

func SelectCourse(course string, ID int) utils.Response {
	A, B, err := judge(course, ID)
	//如果出问题，，，
	if !errors.Is(err, utils.Ok) {
		return err
	}
	//看看课程容量够不够
	if A.Amount >= 50 {
		return utils.Response{Status: "40003", Info: "this course is full!"}
	}
	//给学生的选课表加上对应课程
	//给课程表选择学生数量+1
	result1 := DB.Model(&model.Student{}).Where("name=?", B.Name).Update("Selection", B.Selection+","+course)
	result2 := DB.Model(&model.Course{}).Where("name=?", course).Update("amount", A.Amount+1)
	//这能创建失败，肯定是数据库的问题咯。
	if result1.Error != nil {
		return utils.InternalError(result1.Error)
	}
	if result2.Error != nil {
		return utils.InternalError(result2.Error)
	}
	return utils.Ok
}
func QuitCourse(ID int, course string) utils.Response {
	A, B, err := judge(course, ID)
	if !errors.Is(err, utils.Ok) {
		return err
	}
	//给学生的选课表减去对应课程
	//选课数量-1
	C1 := strings.ReplaceAll(B.Selection, ","+course, "")
	result1 := DB.Model(&model.Student{}).Where("name=?", B.Name).Update("Selection", C1)
	result2 := DB.Model(&model.Course{}).Where("name=?", course).Update("amount", A.Amount-1)
	//删除失败，肯定是数据库的问题咯。
	if result1.Error != nil {
		return utils.InternalError(result1.Error)
	}
	if result2.Error != nil {
		return utils.InternalError(result2.Error)
	}
	return utils.Ok
}
