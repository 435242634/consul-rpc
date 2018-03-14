namespace java cn.flysheep.thriftserver.service

struct UserDto {
  1: i32 id
  2: string userName
}

service UserService {
  UserDto getUser()
}