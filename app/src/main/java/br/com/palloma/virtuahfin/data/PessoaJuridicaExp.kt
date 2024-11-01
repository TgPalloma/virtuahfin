package br.com.palloma.virtuahfin.data

import br.com.palloma.virtuahfin.dao.PessoaJuridicaDao
import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.model.TipoContrato

class PessoaJuridicaExp() {

    val pessoaJuridicaDao = PessoaJuridicaDao()

    fun dadosDeExperiencia() {
        pessoaJuridicaDao.salvar(
            PessoaJuridica(
                "Palloma Tech",
                "Pallominha Inc",
                "32254458000132",
                "Palloma Muller",
                51994200462,
                "palloma.muller@hotmail.com",
                TipoContrato.CLIENTE
            )
        )
        pessoaJuridicaDao.salvar(
            PessoaJuridica(
                "Virtuah Assessoria Remota",
                "Raquel Oliveira Santos",
                "54178958000154",
                "Raquel Santos",
                51994202335,
                "virtuah@gmail.com",
                TipoContrato.ADMINISTRATOR
            )
        )
        pessoaJuridicaDao.salvar(
            PessoaJuridica(
                "Gestor Technology",
                "Gestor Desenvolvimento de Softwares LTDA",
                "78541258000154",
                "Paulo da Cunha",
                51991456785,
                "paulo.cunha@gmail.com",
                TipoContrato.CLIENTE
            )
        )
        pessoaJuridicaDao.salvar(
            PessoaJuridica(
                "Salles Marck",
                "Salles Associados ME",
                "54875632000178",
                "Giovanna Helbitch",
                51991134297,
                "giovanahel@sallesm.com",
                TipoContrato.CLIENTE
            )
        )
        pessoaJuridicaDao.desativarCadastro(pessoaJuridicaDao.acessarLista()[2])
        pessoaJuridicaDao.salvar(
            PessoaJuridica(
                "Macerick Produções",
                "Felipe Jurandino ME",
                "54875632000178",
                "Felipe Jurandino",
                51991336547,
                "felipe.78@hotmail.com",
                TipoContrato.CLIENTE
            )
        )
        pessoaJuridicaDao.salvar(
            PessoaJuridica(
                "Ana Paula de Oliveira",
                "Ana Paula de Oliveira MEI",
                "54875632000178",
                "Ana Paula",
                51987456985,
                "felipe.78@hotmail.com",
                TipoContrato.ASSISTENTE
            )
        )
        pessoaJuridicaDao.salvar(
            PessoaJuridica(
                "Juliana Sabino",
                "Juliana Sabino MEI",
                "54875632000178",
                "Juliana",
                51991142654,
                "juliana.assistente@hotmail.com",
                TipoContrato.ASSISTENTE
            )
        )
        pessoaJuridicaDao.salvar(
            PessoaJuridica(
                "Fabiana Monteiro",
                "Fabiana Monteiro MEI",
                "54875632000178",
                "Fabiana",
                51981475465,
                "fabiana.monteiro@yahoo.com",
                TipoContrato.ASSISTENTE
            )
        )
        pessoaJuridicaDao.desativarCadastro(pessoaJuridicaDao.acessarLista()[6])
    }
}