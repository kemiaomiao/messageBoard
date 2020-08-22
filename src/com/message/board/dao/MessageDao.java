package com.message.board.dao;

import com.message.board.entity.Message;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/14 16:49
 */
public class MessageDao extends BaseDao<Message>{

    static List<Message> MOCK_MESSAGE_LIST = new ArrayList<>();
    static {
        MOCK_MESSAGE_LIST.add(new Message(1l,"小区门禁系统升级，请各位住户留意通知",1l,
                "管理员","小区门禁系统升级，请各位住户留意通知",1,1,"2020-01-25","2020-01-25"));
        MOCK_MESSAGE_LIST.add(new Message(2l,"小区车库积水严重影响通行",2l,
                "张小花","小区车库积水严重影响通行",1,1,"2020-01-24","2020-01-24"));
        MOCK_MESSAGE_LIST.add(new Message(3l,"疫情期间出门需要办理什么手续？(已解决)",3l,
                "王大锤","疫情期间出门需要办理什么手续？(已解决)",1,1,"2020-01-18","2020-01-18"));
        MOCK_MESSAGE_LIST.add(new Message(4l,"小区13号楼666室下水道堵塞(已解决)",1l,
                "管理员","小区13号楼666室下水道堵塞(已解决)",1,1,"2020-01-02","2020-01-02"));
    }

    @Override
    public int save(Message entity) throws Exception {
        if(DataSource.mock){
            entity.setId(new Long(MOCK_MESSAGE_LIST.size() + 1));
            MOCK_MESSAGE_LIST.add(entity);
            return 1;
        }
        String sql = "insert into message(title,authorId,authorName,content,resolve,status,publishDate,updateDate)" +
                " value(?,?,?,?,?,?,?,?)";

        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setString(1,entity.getTitle());
        ps.setLong(2,entity.getAuthorId());
        ps.setString(3,entity.getAuthorName());
        ps.setString(4,entity.getContent());
        ps.setInt(5,entity.getResolve());
        ps.setInt(6,entity.getStatus());
        ps.setString(7,entity.getPublishDate());
        ps.setString(8,entity.getUpdateDate());
        return ps.executeUpdate();
    }

    @Override
    public int delete(Long id) throws Exception {
        if(DataSource.mock){
            Message oldMessage = MOCK_MESSAGE_LIST.stream()
                    .filter((message) -> id.equals(message.getId())).findFirst().orElse(null);
            if(oldMessage != null){
                MOCK_MESSAGE_LIST.remove(oldMessage);
                return 1;
            }
            return 0;
        }
        String sql = "delete from message where id = ? ";

        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setLong(1,id);
        return ps.executeUpdate();
    }

    @Override
    public int update(Message entity) throws Exception {
        if(DataSource.mock){
            Message oldMessage = MOCK_MESSAGE_LIST.stream()
                    .filter((message) -> entity.getId().equals(message.getId())).findFirst().orElse(null);
            if(oldMessage != null){
                oldMessage.setAuthorId(entity.getAuthorId());
                oldMessage.setAuthorName(entity.getAuthorName());
                oldMessage.setContent(entity.getContent());
                oldMessage.setPublishDate(entity.getPublishDate());
                return 1;
            }
            return 0;
        }
        String sql = "update message set authorId = ? ,authorName = ?,content = ?," +
                "publishDate = ? where id = ?";

        PreparedStatement ps = getConnection().prepareStatement(sql);

        ps.setLong(1,entity.getAuthorId());
        ps.setString(2,entity.getAuthorName());
        ps.setString(3,entity.getContent());
        ps.setString(4,entity.getPublishDate());
        ps.setLong(5,entity.getId());
        return ps.executeUpdate();
    }

    @Override
    public List<Message> selectAll(Long id) throws Exception {
        if(DataSource.mock){
            if(id == null){
                return MOCK_MESSAGE_LIST;
            }
            return MOCK_MESSAGE_LIST.stream()
                    .filter((message) -> id.equals(message.getId())).collect(Collectors.toList());
        }
        String sql = "select * from message where 1=1";
        if(id != null){
            sql += " and id = ? ";
        }

        PreparedStatement ps = getConnection().prepareStatement(sql);
        if(id != null) {
            ps.setLong(1, id);
        }

        ResultSet rs = ps.executeQuery();

        // 获取结果集
        List<Message> list = new ArrayList<>();
        while (rs.next()) {
            //title,authorId,authorName,content,resolve,status,publishDate,updateDate
            Long messageId = rs.getLong("id");
            String title = rs.getString("title");
            Long authorId = rs.getLong("authorId");
            String authorName = rs.getString("authorName");
            String content = rs.getString("content");
            Integer resolve = rs.getInt("resolve");
            Integer status = rs.getInt("status");
            String publishDate = rs.getString("publishDate");
            String updateDate = rs.getString("updateDate");

            Message message = new Message(messageId,title,authorId,authorName,content,resolve,status,publishDate,updateDate);
            list.add(message);
        }
        return list;
    }
}
