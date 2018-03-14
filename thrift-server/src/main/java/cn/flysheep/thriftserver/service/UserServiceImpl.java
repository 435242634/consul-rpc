package cn.flysheep.thriftserver.service;

import org.apache.thrift.TException;

public class UserServiceImpl implements UserService.Iface {

    @Override
    public UserDto getUser() throws TException {
        UserDto userDto = new UserDto(1, "哈哈");
        return userDto;
    }
}
