def trace(func):
  def wrapper():
      print(func.__name__, '함수 start') #기능 추가
      func() #기존 함수 호출, func() == helloworld()
      print(func.__name__, '함수 end') #기능 추가
  return wrapper


@trace #Decorator 적용
def helloworld():
  print('helloworld')

helloworld() #함수 그대로 호출
