package utils

type Response struct {
	Status string `json:"status"`
	Info   string `json:"info"`
}

type FinalResponse struct {
	Status string      `json:"status"`
	Info   string      `json:"info"`
	Data   interface{} `json:"data"`
}

var (
	Ok            = Response{"OK", "success"}
	WrongName     = Response{"40001", "wrong username"}
	WrongPWd      = Response{"40002", "wrong Password"}
	RepeatAccount = Response{"40003", "repeat account"}
	RepeatCourse  = Response{"40004", "repeat course"}
)

func InternalError(err error) Response {
	return Response{
		Status: "500",
		Info:   err.Error()}
}

func (r Response) Error() string {
	return r.Info
}
