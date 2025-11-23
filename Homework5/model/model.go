package model

import "time"

type Student struct {
	Id        int `gorm:"primary_key"`
	Name      string
	Grade     int
	Selection string
	Role      string
	Account   int
	Password  string
	CreatedAt time.Time
}
type Course struct {
	Id        int `gorm:"primary_key"`
	Name      string
	Amount    int
	CreatedAt time.Time
}
