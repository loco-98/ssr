package cn.itcast.goods.user.domain;

/**
 * �û�ģ��ʵ����
 * @author qdmmy6
 *
 */
/*
 * ����������
 * 1. t_user����Ϊ������Ҫ��t_user���ѯ�������ݷ�װ��User������
 * 2. ��ģ�����б�����Ϊ������Ҫ�ѱ����ݷ�װ��User������
 */
public class User {
	// ��Ӧ���ݿ��
	private String uid;//����
	private String loginname;//��¼��
	private String loginpass;//��¼����
	private String email;//����
	private boolean status;//״̬��true��ʾ�Ѽ������δ����
	private String activationCode;//�����룬����Ψһֵ����ÿ���û��ļ������ǲ�ͬ�ģ�
	
	// ע���
	private String reloginpass;//ȷ������
	private String verifyCode;//��֤��
	
	// �޸������
	private String newpass;//������

	public String getReloginpass() {
		return reloginpass;
	}
	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}


	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getLoginpass() {
		return loginpass;
	}
	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public String getNewpass() {
		return newpass;
	}
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", loginname=" + loginname + ", loginpass="
				+ loginpass + ", email=" + email + ", status=" + status
				+ ", activationCode=" + activationCode + ", reloginpass="
				+ reloginpass + ", verifyCode=" + verifyCode + ", newpass="
				+ newpass + "]";
	}

}
