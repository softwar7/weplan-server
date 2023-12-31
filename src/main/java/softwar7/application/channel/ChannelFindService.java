package softwar7.application.channel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softwar7.domain.channel.Channel;
import softwar7.domain.member.persist.Member;
import softwar7.mapper.channel.ChannelMapper;
import softwar7.mapper.channel.dto.ChannelResponse;
import softwar7.repository.channel.ChannelRepository;
import softwar7.repository.member.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelFindService {

    private final ChannelRepository channelRepository;
    private final MemberRepository memberRepository;

    public ChannelFindService(final ChannelRepository channelRepository, final MemberRepository memberRepository) {
        this.channelRepository = channelRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public ChannelResponse findChannel(final long channelId) {
        Channel channel = channelRepository.getById(channelId);
        Member member = memberRepository.getById(channel.getMemberId());
        return ChannelMapper.toChannelResponse(channel, member.getUsername());
    }

    @Transactional(readOnly = true)
    public List<ChannelResponse> findAll() {
        List<Channel> channels = channelRepository.findAll();

        List<ChannelResponse> channelResponses = new ArrayList<>();
        for (Channel channel : channels) {
            Member member = memberRepository.getById(channel.getMemberId());
            ChannelResponse channelResponse = ChannelMapper.toChannelResponse(channel, member.getUsername());
            channelResponses.add(channelResponse);
        }

        return channelResponses;
    }
}
