package dev.hiok.portfoliosocialauthserver.core.security.oauth2;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import dev.hiok.portfoliosocialauthserver.domain.model.SocialId;
import dev.hiok.portfoliosocialauthserver.domain.repository.SocialIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hiok.portfoliosocialauthserver.core.security.UserPrincipal;
import dev.hiok.portfoliosocialauthserver.core.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import dev.hiok.portfoliosocialauthserver.core.security.oauth2.userInfo.OAuth2UserInfo;
import dev.hiok.portfoliosocialauthserver.core.security.oauth2.userInfo.OAuth2UserInfoFactory;
import dev.hiok.portfoliosocialauthserver.domain.model.AuthProvider;
import dev.hiok.portfoliosocialauthserver.domain.model.CommonsGroup;
import dev.hiok.portfoliosocialauthserver.domain.model.Group;
import dev.hiok.portfoliosocialauthserver.domain.model.User;
import dev.hiok.portfoliosocialauthserver.domain.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SocialIdRepository socialIdRepository;

  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOauth2UserInfo(
      userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
    if (oAuth2UserInfo.getEmail().isEmpty()) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }

    Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
    User user;
    if (userOptional.isPresent()) {
      user = userOptional.get();

      if (!user.getSocialIds().stream().map(SocialId::getProvider).toList()
        .contains(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()))) {
          SocialId newSocialId = new SocialId();
          newSocialId.setProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()));
          newSocialId.setSocialId(oAuth2UserInfo.getId());
          newSocialId.setUserId(user.getId());
          socialIdRepository.save(newSocialId);
      }

      user = updateExistingUser(user, oAuth2UserInfo);
    } else {
      user = registerNewUser(userRequest, oAuth2UserInfo);
    }
    
    return UserPrincipal.create(user, oAuth2User.getAttributes(), getAuthorities(user));
  }
  
  private Collection<GrantedAuthority> getAuthorities(User user) {
  	return user.getGroups().stream()
  			.flatMap(group -> group.getRoles().stream()
  				.map(role -> new SimpleGrantedAuthority(role.getName())))
  			.collect(Collectors.toList());
  }

  private User registerNewUser(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
    User user = new User();
    user.setName(oAuth2UserInfo.getName());
    user.setEmail(oAuth2UserInfo.getEmail());
    user.setImageUrl(oAuth2UserInfo.getImageUrl());
    user.addGroup(Group.getGroup(CommonsGroup.COMMON_USER));

    User savedUser = userRepository.save(user);

    SocialId newSocialId = new SocialId();
    newSocialId.setProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()));
    newSocialId.setSocialId(oAuth2UserInfo.getId());
    newSocialId.setUserId(savedUser.getId());
    socialIdRepository.save(newSocialId);

    return userRepository.getById(savedUser.getId());
  }

  private User updateExistingUser(User user, OAuth2UserInfo oAuth2UserInfo) {
    user.setName(oAuth2UserInfo.getName());
    user.setImageUrl(oAuth2UserInfo.getImageUrl());
    
    return userRepository.save(user);
  }
  
}
