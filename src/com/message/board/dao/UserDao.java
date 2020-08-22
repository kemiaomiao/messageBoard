package com.message.board.dao;

import com.message.board.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/14 15:45
 */
public class UserDao extends BaseDao<User>{

    static List<User> MOCK_USER_LIST = new ArrayList<>();
    static {
        MOCK_USER_LIST.add(new User(1l,"admin","admin","管理员",1));
        MOCK_USER_LIST.add(new User(2l,"1001","1001","张小花",0));
        MOCK_USER_LIST.add(new User(3l,"1002","1002","王大锤",0));
    }


    @Override
    public int save(User entity) throws Exception {
        if(DataSource.mock){
            entity.setId(new Long(MOCK_USER_LIST.size() + 1));
            MOCK_USER_LIST.add(entity);
            return 1;
        }
        String sql = "insert into user(account,password,name,role) value(?,?,?,?)";

        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setString(1,entity.getAccount());
        ps.setString(2,entity.getPassword());
        ps.setString(3,entity.getName());
        ps.setInt(4,entity.getRole());
        return ps.executeUpdate();
    }

    @Override
    public int delete(Long id) throws Exception {
        if(DataSource.mock){
            User oldUser = MOCK_USER_LIST.stream()
                    .filter((user) -> id.equals(user.getId())).findFirst().orElse(null);
            if(oldUser != null){
                MOCK_USER_LIST.remove(oldUser);
                return 1;
            }
            return 0;
        }
        String sql = "delete from user where id = ? ";

        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setLong(1,id);
        return ps.executeUpdate();
    }

    @Override
    public int update(User entity) throws Exception {
        if(DataSource.mock){
            User oldUser = MOCK_USER_LIST.stream()
                    .filter((user) -> entity.getId().equals(user.getId())).findFirst().orElse(null);
            if(oldUser != null){
                oldUser.setAccount(entity.getAccount());
                oldUser.setPassword(entity.getPassword());
                oldUser.setName(entity.getName());
                oldUser.setRole(entity.getRole());
                return 1;
            }
            return 0;
        }
        String sql = "update user set account = ?, password = ?, name = ?, role = ? where id = ? ";

        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setString(1,entity.getAccount());
        ps.setString(2,entity.getPassword());
        ps.setString(3,entity.getName());
        ps.setInt(4,entity.getRole());
        ps.setLong(5,entity.getId());
        return ps.executeUpdate();
    }

    @Override
    public List<User> selectAll(Long id) throws Exception {
        if(DataSource.mock){
            if(id == null){
               return MOCK_USER_LIST;
            }
            return MOCK_USER_LIST.stream()
                    .filter((user) -> id.equals(user.getId())).collect(Collectors.toList());
        }
        String sql = "select * from user where 1=1";
        if(id != null){
            sql += " and id = ? ";
        }

        PreparedStatement ps = getConnection().prepareStatement(sql);
        if(id != null) {
            ps.setLong(1, id);
        }

        ResultSet rs = ps.executeQuery();

        // 获取结果集
        List<User> list = new ArrayList<>();
        while (rs.next()) {
            Long userId = rs.getLong("id");
            String account = rs.getString("account");
            String password = rs.getString("password");
            String name = rs.getString("name");
            Integer role = rs.getInt("role");

            User user = new User(userId,account,password,name,role);
            list.add(user);
        }
        return list;
    }

    public User selectByAccountAndPwd(String account, String pwd) throws Exception {

        if(DataSource.mock){
            return MOCK_USER_LIST.stream()
                    .filter((user) -> account.equals(user.getAccount()) && (pwd == null || pwd.equals(user.getPassword())))
                    .findFirst().orElse(null);
        }
        String sql = "select * from user where account = ?";
        if(pwd != null){
            sql += " and password = ?";
        }
//        String sql = "select * from user where account = '" + account + "' and password = '" + pwd + "'" ;
        PreparedStatement ps = getConnection().prepareStatement(sql); //' or '1'='1
        ps.setString(1,account);
        if(pwd != null) {
            ps.setString(2, pwd);
        }
        ResultSet rs = ps.executeQuery();
        // 获取结果集
        List<User> list = new ArrayList<>();
        while (rs.next()) {
            Long userId = rs.getLong("id");
            String userAccount = rs.getString("account");
            String name = rs.getString("name");
            Integer role = rs.getInt("role");
            User user = new User(userId,userAccount,null,name,role);
            list.add(user);
        }
        return list.size() > 0 ? list.get(0) : null;
    }

}
