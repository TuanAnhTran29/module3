package com.service.loancardservice;

import com.model.LoanCard;
import com.model.Student;
import com.service.IGeneralService;

import java.util.List;

public interface ILoanCardService extends IGeneralService<LoanCard> {
    public void add(LoanCard loanCard);

    public List<LoanCard> findAll();

    public LoanCard findById(int id);

    public void update(int id,LoanCard loanCard);

    public void delete(int id);

    public List<String> getAllCode();

    public List<LoanCard> findByStudentId(int id);

    public List<LoanCard> findByBookName(String bookName);
}
