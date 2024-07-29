/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.ExternalTokens;
import fpt.aptech.project.entities.Users;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface IExternalTokensService {
    public List<ExternalTokens> findAll();

    public void createExternalTokens(ExternalTokens externalTokens);

    public ExternalTokens findOne(int externalTokensId);

    public void updateExternalTokens(ExternalTokens externalTokens);

    public void deleteExternalTokens(int externalTokensId);
    
    public ExternalTokens findByUserId(Users users);
}
