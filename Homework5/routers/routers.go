package routers

import (
	"Homework5/api"
	"Homework5/utils"

	"github.com/gin-gonic/gin"
)

type RegisterRequest struct {
	Account  int    `json:"account"`
	Password string `json:"password"`
	Name     string `json:"name"`
	Grade    int    `json:"grade"`
}

func Router(h *gin.Engine) {
	public := h.Group("/user")
	{
		public.POST("/logon", api.LogonHandler)
		public.POST("/register", api.RegisterHandler)
		public.GET("/checkCourse", api.CheckCourseHandler)
	}

	private := public.Group("/admin")
	private.Use(utils.LoggerMiddleware())
	{
		public.GET("/createCourse/:cour", api.CreateCourseHandler)
		public.GET("/selectCourse/:cour", api.SelectCourseHandler)
		public.GET("/quitCourse/:cour", api.QuitCourseHandler)
		public.GET("/checkSelectedCourse", api.CheckSelectedCourseHandler)
	}
	err := h.Run()
	if err != nil {
		panic(err)
	}
}
