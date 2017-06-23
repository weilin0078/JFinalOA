CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `v_tasklist_candidate` AS SELECT TASK_ID_, USER_ID_ USER_ID
                    FROM ACT_RU_IDENTITYLINK I, ACT_RU_TASK T
                      WHERE TASK_ID_ IS NOT NULL
                        AND USER_ID_ IS NOT NULL
                        AND I.TASK_ID_ = T.ID_
                        AND T.ASSIGNEE_ IS NULL
                        AND TYPE_ = 'candidate';

CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `v_tasklist_candidate_d` AS SELECT DISTINCT * FROM v_tasklist_candidate U;
						
CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `v_tasklist` AS SELECT A.ID_ AS TASKID,
       A.PROC_INST_ID_ AS INSID,
       A.TASK_DEF_KEY_ AS TASKDEFKEY,
       D.KEY_ AS DEFKEY,
       D.NAME_ AS DEFNAME,
       A.NAME_ AS TASKNAME,
       A.ASSIGNEE_ AS ASSIGNEE,
			 I.USER_ID CANDIDATE,
       A.PROC_DEF_ID_ AS DEFID,
       A.DELEGATION_ AS DELEGATIONID,
       A.DESCRIPTION_ AS DESCRIPTION,
       date_format(A.CREATE_TIME_,'%Y-%m-%d') AS CREATETIME,
       date_format(A.DUE_DATE_,'%Y-%m-%d') AS DUEDATE
  FROM ACT_RU_TASK A
  LEFT JOIN V_TASKLIST_CANDIDATE_D I
    ON A.ID_ = I.TASK_ID_ 
   LEFT JOIN ACT_RE_PROCDEF D 
   ON A.PROC_DEF_ID_ = D.ID_;