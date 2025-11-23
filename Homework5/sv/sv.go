package sv

import (
	"Homework5/dao"
	"Homework5/model"
	"Homework5/utils"
	"errors"
	"sync"
)

var mu sync.Mutex

func Register(pass string, name string, grade int, acc int) utils.Response {
	for _, j := range name {
		a := "!@#$%^&*()_+:{}|<>?“”——】【[]、/，。,."
		for _, i := range a {
			if i == j {
				return utils.Response{Status: "40001", Info: "名字不可包括特殊字符"}
			}
		}
	}

	//判断密码
	if len(pass) < 6 {
		return utils.Response{Status: "40002", Info: "密码不可少于六个字"}
	} else {
		for _, r := range pass {
			if r >= '\u4e00' && r <= '\u9fa5' {
				return utils.Response{Status: "40001", Info: "密码不可含有中文字符"}
			}
		}
	}
	//判断账号
	if acc < 100000 {
		return utils.Response{Status: "40001", Info: "账号不可少于六个字"}
	}

	err := dao.Register(pass, name, grade, acc)
	if !errors.Is(err, utils.Ok) {
		return err
	}
	return utils.Ok
}

func Logon(pass string, acc int) (uint64, string, utils.Response) {
	ID, role, err := dao.Logon(pass, acc)
	if !errors.Is(err, utils.Ok) {
		return 0, "", err
	}
	return ID, role, utils.Ok
}
func CreateCourse(name string) utils.Response {
	err := dao.CreateCourse(name)
	if !errors.Is(err, utils.Ok) {
		return err
	}
	return utils.Ok
}
func CheckCourse() []model.Course {
	A := dao.CheckCourse()
	return A
	//这个应该是谁都能用吧？
}
func CheckSelectedCourse(ID int) (model.Student, utils.Response) {
	A, err := dao.CheckSelectedCourse(ID)
	if !errors.Is(err, utils.Ok) {
		return model.Student{}, err
	}
	return A, utils.Ok
}
func SelectCourse(name string, ID int) utils.Response {
	mu.Lock()
	defer mu.Unlock()
	err := dao.SelectCourse(name, ID)
	if !errors.Is(err, utils.Ok) {
		return err
	}
	return utils.Ok
}
func QuitCourse(courseName string, ID int) utils.Response {
	err := dao.QuitCourse(ID, courseName)
	if !errors.Is(err, utils.Ok) {
		return err
	}
	return utils.Ok
}
